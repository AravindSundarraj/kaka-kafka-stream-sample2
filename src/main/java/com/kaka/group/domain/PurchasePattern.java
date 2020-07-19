
package com.kaka.group.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.NoArgsConstructor;
import lombok.ToString;
//import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "zipCode",
    "item",
    "date",
    "amount"
})
@ToString
@NoArgsConstructor
public class PurchasePattern {

    @JsonProperty("zipCode")
    private String zipCode;
    @JsonProperty("item")
    private String item;
    //@JsonDeserialize(using = LocalDateDeserializer.class)
   // @JsonSerialize(using = LocalDateSerializer.class)
  //  @JsonProperty("date")
    @JsonIgnore
    private LocalDate date;
    @JsonProperty("amount")
    private BigDecimal amount;





        private PurchasePattern(Builder builder) {
            zipCode = builder.zipCode;
            item = builder.item;
            date = builder.date;
            amount = builder.amount;

        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static Builder builder(Purchase purchase){
            return new Builder(purchase);

        }
        public String getZipCode() {
            return zipCode;
        }

        public String getItem() {
            return item;
        }

        public LocalDate getDate() {
            return date;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return "PurchasePattern{" +
                    "zipCode='" + zipCode + '\'' +
                    ", item='" + item + '\'' +
                    ", date=" + date +
                    ", amount=" + amount +
                    '}';
        }

/*        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PurchasePattern)) return false;

            PurchasePattern that = (PurchasePattern) o;

            if (Double.compare(that.amount, amount) != 0) return false;
            if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null) return false;
            return item != null ? item.equals(that.item) : that.item == null;
        }*/

/*        @Override
        public int hashCode() {
            int result;
            long temp;
            result = zipCode != null ? zipCode.hashCode() : 0;
            result = 31 * result + (item != null ? item.hashCode() : 0);
            temp = Double.doubleToLongBits(amount);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }*/

        public static final class Builder {
            private String zipCode;
            private String item;
            private LocalDate date;
            private BigDecimal amount;

            private  Builder() {
            }

            private Builder(Purchase purchase) {
                this.zipCode = purchase.getZipCode();
                this.item = purchase.getItemPurchased();
                this.date = purchase.getPurchaseDate();

                this.amount = purchase.getPrice().multiply(BigDecimal.
                        valueOf(purchase.getQuantity()));
            }

            public Builder zipCode(String val) {
                zipCode = val;
                return this;
            }

            public Builder item(String val) {
                item = val;
                return this;
            }

            public Builder date(LocalDate val) {
                date = val;
                return this;
            }

            public Builder amount(BigDecimal amount) {
                this.amount = amount;
                return this;
            }

            public PurchasePattern build() {
                return new PurchasePattern(this);
            }
        }
    }


