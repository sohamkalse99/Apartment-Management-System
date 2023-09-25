package com.company.apartmentsmanagementsystem.Repo;

import com.company.apartmentsmanagementsystem.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;

/*public interface OwnerRepo extends CrudRepository<Owner, Integer> {
}*/

public interface OwnerRepo extends JpaRepository<Owner, Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Owner o set o.bills =:bills, o.email=:email, o.firstname=:firstName, o.lastname=:lastName, o.parking_num=:parkingNumber where o.build_id=:buildId and o.apartmentNumber=:apartmentNumber")
//    @Query("select a from ")
    public void updateQuery(@Param("buildId") int buildId, @Param("apartmentNumber") int apartmentNumber, @Param("bills") int bills, @Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("parkingNumber") int parkingNumber);

    @Query("select o from Owner o where o.apartmentNumber=:apartmentNumber and o.build_id=:buildId")
    public Owner getOwnerDetails(@Param("apartmentNumber") int apartmentNumber, @Param("buildId") int buildId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from Owner o where o.apartmentNumber=:apartmentNumber and o.build_id=:buildId")
    public void deleteOwnerDetails(@Param("buildId") int buildId, @Param("apartmentNumber") int apartmentNumber);
}

