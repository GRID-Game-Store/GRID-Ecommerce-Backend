package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;
import com.khomsi.backend.main.admin.service.AdminTransactionService;
import com.khomsi.backend.main.checkout.mapper.TransactionMapper;
import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.checkout.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.khomsi.backend.main.utils.SortingUtils.createSorting;

@Service
@RequiredArgsConstructor
public class AdminTransactionServiceImpl implements AdminTransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    //TODO
    // add 'edit', 'delete|cancel' transaction. Add genres, platforms, dev, tags, publishers add|edit|delete
    // add metrics for sales from paypal, stripe to view on main admin page
    @Override
    public AdminModelResponse getAllTransactions(EntityModelRequest entityModelRequest) {
        int page = entityModelRequest.getPage();
        Pageable pageable = PageRequest.of(page, entityModelRequest.getSize(),
                createSorting(entityModelRequest.getSort(), "createdAt"));
        Page<Transaction> transactionPage = transactionRepository.findAll(pageable);

        List<TransactionDTO> transactions = transactionPage.getContent().stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .toList();

        return AdminModelResponse.builder()
                .entities(transactions)
                .totalItems(transactionPage.getTotalElements())
                .totalPages(transactionPage.getTotalPages() - 1)
                .currentPage(page)
                .build();
    }
}
