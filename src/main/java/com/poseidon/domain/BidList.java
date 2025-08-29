package com.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "bidlist")
public class BidList implements BaseEntity<BidList>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BidListId", nullable = false)
    private Integer bidListId;

    @Size(max = 30)
    @NotBlank(message = "Account is mandatory")
    @Column(name = "account", nullable = false, length = 30)
    private String account;

    @Size(max = 30)
    @NotBlank(message = "Type is mandatory")
    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "bidQuantity")
    private Double bidQuantity;

    @Column(name = "askQuantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Size(max = 125)
    @Column(name = "benchmark", length = 125)
    private String benchmark;

    @Column(name = "bidListDate")
    private Date bidListDate;

    @Size(max = 125)
    @Column(name = "commentary", length = 125)
    private String commentary;

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
    @Column(name = "book", length = 125)
    private String book;

    @Size(max = 125)
    @Column(name = "creationName", length = 125)
    private String creationName;

    @Column(name = "creationDate")
    private Date creationDate;

    @Size(max = 125)
    @Column(name = "revisionName", length = 125)
    private String revisionName;

    @Column(name = "revisionDate")
    private Date revisionDate;

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

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public Integer getId(){
        return bidListId;
    }

    public void update(BidList bidList){
        this.account = bidList.getAccount();
        this.type = bidList.getType();
        this.bidQuantity = bidList.getBidQuantity();
    }
}