package com.khomsi.backend.main.checkout.model.enums;

public enum EmailTemplates {
    PURCHASE_CONFIRMATION("purchase_confirmation_email", "Thanks for purchase in GRID"),
    DISCOUNT_NOTIFICATION("discount_notification_email", "Discount Notification");

    private final String templateName;
    private final String subject;

    EmailTemplates(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getSubject() {
        return subject;
    }
}
