package com.cerpo.fd.model.retailer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailerAddressRepository extends JpaRepository<RetailerAddress, Integer> {
}
