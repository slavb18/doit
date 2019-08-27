/**
 * This file was generated by the JPA Modeler
 */
package ru.ilb.doit.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.*;

/**
 * Работа
 *
 * @author slavb
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Work implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private LocalDate workDate;

    @Basic
    private BigDecimal price;

    @Basic
    private String resourceLink;

    /**
     * Длительность
     */
    @Basic
    private Integer duration;

    @ManyToOne(targetEntity = BussinessEntity.class)
    private BussinessEntity bussinessEntity;

    @XmlTransient
    @OneToMany(targetEntity = Bid.class, mappedBy = "work")
    private List<Bid> bids;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getWorkDate() {
        return this.workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getResourceLink() {
        return this.resourceLink;
    }

    public void setResourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BussinessEntity getBussinessEntity() {
        return this.bussinessEntity;
    }

    public void setBussinessEntity(BussinessEntity bussinessEntity) {
        this.bussinessEntity = bussinessEntity;
    }

    public List<Bid> getBids() {
        return this.bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

}
