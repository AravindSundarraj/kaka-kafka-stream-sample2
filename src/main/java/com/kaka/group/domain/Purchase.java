
package com.kaka.group.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.NoArgsConstructor;
import lombok.ToString;
//import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "firstName",
        "lastName",
        "customerId",
        "cardNumber",
        "itemPurchased",
        "department",
        "employeeId",
        "quantity",
        "price",
        "purchaseDate",
        "zipCode",
        "storeId"
})
@ToString
@NoArgsConstructor
public class Purchase {

    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("creditCardNumber")
    private String creditCardNumber;
    @JsonProperty("itemPurchased")
    private String itemPurchased;
    @JsonProperty("department")
    private String department;
    @JsonProperty("employeeId")
    private String employeeId;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)

    @JsonProperty("purchaseDate")
    private LocalDate purchaseDate;
    @JsonProperty("zipCode")
    private String zipCode;
    @JsonProperty("storeId")
    private String storeId;


    private Purchase(PurchaseBuilder purchaseBuilder) {

        firstName = purchaseBuilder.firstName;
        lastName = purchaseBuilder.lastName;
        customerId = purchaseBuilder.customerId;
        creditCardNumber = purchaseBuilder.creditCardNumber;
        itemPurchased = purchaseBuilder.itemPurchased;
        quantity = purchaseBuilder.quantity;
        price = purchaseBuilder.price;
        purchaseDate = purchaseBuilder.purchaseDate;
        zipCode = purchaseBuilder.zipCode;
        employeeId = purchaseBuilder.employeeId;
        department = purchaseBuilder.department;
        storeId = purchaseBuilder.storeId;
    }

    public static PurchaseBuilder builder() {
        return new PurchaseBuilder();
    }

    public static PurchaseBuilder builder(Purchase copy) {
        PurchaseBuilder builder = new PurchaseBuilder();
        builder.firstName = copy.firstName;
        builder.lastName = copy.lastName;
        builder.creditCardNumber = copy.creditCardNumber;
        builder.itemPurchased = copy.itemPurchased;
        builder.quantity = copy.quantity;
        builder.price = copy.price;
        builder.purchaseDate = copy.purchaseDate;
        builder.zipCode = copy.zipCode;
        builder.customerId = copy.customerId;
        builder.department = copy.department;
        builder.employeeId = copy.employeeId;
        builder.storeId = copy.storeId;

        return builder;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Purchase withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Purchase withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonProperty("customerId")
    public String getCustomerId() {
        return customerId;
    }

    @JsonProperty("customerId")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Purchase withCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    @JsonProperty("cardNumber")
    public String getCardNumber() {
        return creditCardNumber;
    }

    @JsonProperty("cardNumber")
    public void setCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Purchase withCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        return this;
    }

    @JsonProperty("itemPurchased")
    public String getItemPurchased() {
        return itemPurchased;
    }

    @JsonProperty("itemPurchased")
    public void setItemPurchased(String itemPurchased) {
        this.itemPurchased = itemPurchased;
    }

    public Purchase withItemPurchased(String itemPurchased) {
        this.itemPurchased = itemPurchased;
        return this;
    }

    @JsonProperty("department")
    public String getDepartment() {
        return department;
    }

    @JsonProperty("department")
    public void setDepartment(String department) {
        this.department = department;
    }

    public Purchase withDepartment(String department) {
        this.department = department;
        return this;
    }

    @JsonProperty("employeeId")
    public String getEmployeeId() {
        return employeeId;
    }

    @JsonProperty("employeeId")
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Purchase withEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Purchase withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    @JsonProperty("price")
    public BigDecimal getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Purchase withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @JsonProperty("purchaseDate")
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    @JsonProperty("purchaseDate")
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Purchase withPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    @JsonProperty("zipCode")
    public String getZipCode() {
        return zipCode;
    }

    @JsonProperty("zipCode")
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Purchase withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    @JsonProperty("storeId")
    public String getStoreId() {
        return storeId;
    }

    @JsonProperty("storeId")
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Purchase withStoreId(String storeId) {
        this.storeId = storeId;
        return this;
    }

 /*   @Override
    public String toString() {
        return new ToStringBuilder(this).append("firstName", firstName).append("lastName", lastName).append("customerId", customerId).append("creditCardNumber", creditCardNumber).append("itemPurchased", itemPurchased).append("department", department).append("employeeId", employeeId).append("quantity", quantity).append("price", price).append("purchaseDate", purchaseDate).append("zipCode", zipCode).append("storeId", storeId).toString();
    }*/

    public static class PurchaseBuilder {

        private String firstName;
        private String lastName;
        private String customerId;
        private String creditCardNumber;
        private String itemPurchased;
        private int quantity;
        private BigDecimal price;
        private LocalDate purchaseDate;
        private String zipCode;
        private String department;
        private String employeeId;
        private String storeId;

        private static final String CC_NUMBER_REPLACEMENT = "xxxx-xxxx-xxxx-";

        private PurchaseBuilder() {
        }

        public PurchaseBuilder firstName(String val) {
            firstName = val;
            return this;
        }

        public PurchaseBuilder lastName(String val) {
            lastName = val;
            return this;
        }


        public PurchaseBuilder maskCreditCard() {
            Objects.requireNonNull(this.creditCardNumber, "Credit Card can't be null");
            String[] parts = this.creditCardNumber.split("-");
            if (parts.length < 4) {
                this.creditCardNumber = "xxxx";
            } else {
                String last4Digits = this.creditCardNumber.split("-")[3];
                this.creditCardNumber = CC_NUMBER_REPLACEMENT + last4Digits;
            }
            return this;
        }

        public PurchaseBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public PurchaseBuilder department(String department) {
            this.department = department;
            return this;
        }

        public PurchaseBuilder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public PurchaseBuilder storeId(String storeId) {
            this.storeId = storeId;
            return this;
        }

        public PurchaseBuilder creditCardNumber(String val) {
            creditCardNumber = val;
            return this;
        }

        public PurchaseBuilder itemPurchased(String val) {
            itemPurchased = val;
            return this;
        }

        public PurchaseBuilder quanity(int val) {
            quantity = val;
            return this;
        }

        public PurchaseBuilder price(BigDecimal val) {
            price = val;
            return this;
        }

        public PurchaseBuilder purchaseDate(LocalDate val) {
            purchaseDate = val;
            return this;
        }

        public PurchaseBuilder zipCode(String val) {
            zipCode = val;
            return this;
        }

        public Purchase build() {
            return new Purchase(this);
        }
    }

}


