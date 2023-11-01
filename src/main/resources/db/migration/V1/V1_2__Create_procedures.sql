######### insert_game PROCEDURE ############
CREATE PROCEDURE insert_game(
    IN game_title VARCHAR(255),
    IN game_description TEXT,
    IN game_release_date DATE,
    IN game_system_requirements TEXT,
    IN game_price DECIMAL(10,2),
    IN game_cover_image_url TEXT,
    IN p_developer_name VARCHAR(255),
    IN p_publisher_name VARCHAR(255),
    IN p_tags TEXT,
    IN p_genres TEXT,
    IN p_platforms TEXT
)
BEGIN
    DECLARE dev_id INT;
    DECLARE publish_id INT;
    DECLARE game_id INT;

    -- Check if the game with the same title already exists
    SELECT game_id INTO game_id
    FROM games
    WHERE LOWER(games.title) = LOWER(game_title);

    -- If a game with the same title does not exist, proceed with insertion
    IF game_id IS NULL THEN
        -- Get developer_id and publisher_id based on their names
        SELECT developer_id INTO dev_id FROM developers WHERE LOWER(name) = LOWER(p_developer_name);
        SELECT publisher_id INTO publish_id FROM publishers WHERE LOWER(name) = LOWER(p_publisher_name);

        -- Check if developer and publisher ids are found, otherwise raise an error
        IF dev_id IS NULL OR publish_id IS NULL THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No matching name for developer or publisher';
        ELSE
            -- Insert game into the games table
            INSERT INTO games (title, description, release_date, system_requirements, price, cover_image_url, developer_id, publisher_id)
            VALUES (game_title, game_description, game_release_date, game_system_requirements, game_price, game_cover_image_url, dev_id, publish_id);

            -- Get the game_id of the newly inserted game
            SET game_id = LAST_INSERT_ID();

            -- PROCESS TAGS --
            -- Replace commas with spaces in the tags string
            -- Split the tags string into individual tags
            WHILE CHAR_LENGTH(p_tags) > 0 DO
                    SET @tag = TRIM(SUBSTRING_INDEX(p_tags, ', ', 1));
                    SET p_tags = TRIM(SUBSTRING(p_tags, CHAR_LENGTH(@tag) + 2));

                    -- Get the tag_id based on the tag name
                    SET @tag_id = NULL;
                    SELECT tag_id INTO @tag_id FROM tags WHERE LOWER(name) = LOWER(@tag);

                    -- If tag doesn't exist, insert it
                    IF @tag_id IS NULL THEN
                        INSERT INTO tags (name) VALUES (@tag);
                        SET @tag_id = LAST_INSERT_ID();
                    END IF;

                    -- Insert into games_has_tags
                    INSERT INTO games_has_tags (tags_id, games_id) VALUES (@tag_id, game_id);
                END WHILE;

            -- PROCESS GENRES --
            WHILE CHAR_LENGTH(p_genres) > 0 DO
                    SET @genre = TRIM(SUBSTRING_INDEX(p_genres, ', ', 1));
                    SET p_genres = TRIM(SUBSTRING(p_genres, CHAR_LENGTH(@genre) + 2));

                    -- Get the genre_id based on the genre name
                    SET @genre_id = NULL;
                    SELECT genre_id INTO @genre_id FROM genres WHERE LOWER(name) = LOWER(@genre);

                    -- If genre doesn't exist, insert it
                    IF @genre_id IS NULL THEN
                        INSERT INTO genres (name) VALUES (@genre);
                        SET @genre_id = LAST_INSERT_ID();
                    END IF;

                    -- Insert into games_has_genres
                    INSERT INTO games_has_genres (genres_id, games_id) VALUES (@genre_id, game_id);
                END WHILE;

            -- PROCESS PLATFORMS --

            -- Split the platforms string into individual platforms
            WHILE CHAR_LENGTH(p_platforms) > 0 DO
                    SET @platform = TRIM(SUBSTRING_INDEX(p_platforms, ', ', 1));
                    SET p_platforms = TRIM(SUBSTRING(p_platforms, CHAR_LENGTH(@platform) + 2));

                    -- Get the platform_id based on the platform name
                    SET @platform_id = NULL;
                    SELECT platform_id INTO @platform_id FROM platforms WHERE LOWER(name) = LOWER(@platform);

                    -- If platform doesn't exist, insert it
                    IF @platform_id IS NULL THEN
                        INSERT INTO platforms (name) VALUES (@platform);
                        SET @platform_id = LAST_INSERT_ID();
                    END IF;

                    -- Insert into games_has_platforms
                    INSERT INTO games_has_platforms (platforms_id, games_id) VALUES (@platform_id, game_id);
                END WHILE;
        END IF;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Game with the same title already exists';
    END IF;
END;
#
DELIMITER ;

######### insert_developer TRIGGER ############
DELIMITER #
CREATE PROCEDURE insert_developer(
    IN p_developer_name VARCHAR(255)
)
BEGIN
    DECLARE developer_count INT;

    SELECT COUNT(*) INTO developer_count
    FROM developers
    WHERE LOWER(name) = LOWER(p_developer_name);

    IF developer_count = 0 THEN
        INSERT INTO developers (name) VALUES (p_developer_name);
    END IF;
END;
#
DELIMITER ;
######### insert_publisher TRIGGER ############
DELIMITER #
CREATE PROCEDURE insert_publisher(
    IN p_publisher_name VARCHAR(255)
)
BEGIN
    DECLARE publisher_count INT;

    SELECT COUNT(*) INTO publisher_count
    FROM publishers
    WHERE LOWER(name) = LOWER(p_publisher_name);

    IF publisher_count = 0 THEN
        INSERT INTO publishers (name) VALUES (p_publisher_name);
    END IF;
END;
#
DELIMITER ;
######### insert_genre TRIGGER ############
DELIMITER #
CREATE PROCEDURE insert_genre(
    IN p_genre_name VARCHAR(255)
)
BEGIN
    DECLARE genre_count INT;

    SELECT COUNT(*) INTO genre_count
    FROM genres
    WHERE LOWER(name) = LOWER(p_genre_name);

    IF genre_count = 0 THEN
        INSERT INTO genres (name) VALUES (p_genre_name);
    END IF;
END;
#
DELIMITER ;