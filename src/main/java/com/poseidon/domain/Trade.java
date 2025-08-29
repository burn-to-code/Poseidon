package com.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "trade")
public class Trade implements BaseEntity<Trade>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TradeId", nullable = false)
    private Integer tradeId;

    @Size(max = 30)
    @NotBlank(message = "Account is mandatory")
    @Column(name = "account", nullable = false, length = 30)
    private String account;

    @Size(max = 30)
    @NotBlank(message = "Type is mandatory")
    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "buyQuantity")
    @Min(value = 0, message = "Buy quantity should be greater than zero")
    @NotNull(message = "Buy quantity is mandatory")
    private Double buyQuantity;

    @Column(name = "sellQuantity")
    private Double sellQuantity;

    @Column(name = "buyPrice")
    private Double buyPrice;

    @Column(name = "sellPrice")
    private Double sellPrice;

    @Column(name = "tradeDate")
    private Instant tradeDate;

    @Size(max = 125)
    @Column(name = "security", length = 125)
    private String security;

    @Size(max = 10)
    @Column(name = "status", length = 10)
    private String status;

    @Size(max = 125)
    @Column(name = "trader", length = 125)
    private String trader;

    @Size(max = 125)
    @Column(name = "benchmark", length = 125)
    private String benchmark;

    @Size(max = 125)
    @Column(name = "book", length = 125)
    private String book;

    @Size(max = 125)
    @Column(name = "creationName", length = 125)
    private String creationName;

    @Column(name = "creationDate")
    @CreationTimestamp
    private Instant creationDate;

    @Size(max = 125)
    @Column(name = "revisionName", length = 125)
    private String revisionName;

    @Column(name = "revisionDate")
    private Instant revisionDate;

    @Size(max = 125)
    @Column(name = "dealName", length = 125)
    private String dealName;

    @Size(max = 125)
    @Column(name = "dealType", length = 125)
    private String dealType;

    @Size(max = 125)
    @Column(name = "sourceListId", length = 125)
    private String sourceListId;

    @Size(max = 125)
    @Column(name = "side", length = 125)
    private String side;

    public Trade(String tradeAccount, String type, Double buyQuantity, Double sellQuantity) {
        this.account = tradeAccount;
        this.type = type;
        this.buyQuantity = buyQuantity;
        this.sellQuantity = sellQuantity;
    }

    public Integer getId(){
        return tradeId;
    }

    @Override
    public void update(Trade trade) {
        this.account = trade.getAccount();
        this.type = trade.getType();
        this.buyQuantity = trade.getBuyQuantity();
        this.sellQuantity = trade.getSellQuantity();
    }
}
