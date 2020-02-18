package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
}
