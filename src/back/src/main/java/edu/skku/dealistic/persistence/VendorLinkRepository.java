package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.VendorLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorLinkRepository extends JpaRepository<VendorLink, Integer> {
}
