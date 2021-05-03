package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * This class provide the necessary methods to access the OrderEntity
 */
@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the Order
     *
     * @param ordersEntity Orders Entity to be Saved
     * @return Saved Order Entity
     */
    public OrdersEntity saveOrder(OrdersEntity ordersEntity) {
        entityManager.persist(ordersEntity);
        return ordersEntity;
    }

    /**
     * Get list of Orders placed by Customer
     *
     * @param customerEntity Customer Entity
     * @return OrdersEntities or NULL
     */
    public List<OrdersEntity> getOrdersByCustomers(CustomerEntity customerEntity) {
        try {
            List<OrdersEntity> ordersEntities = entityManager.createNamedQuery("getOrdersByCustomers", OrdersEntity.class).setParameter("customer", customerEntity).getResultList();
            return ordersEntities;
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Get list of Orders by Restaurant
     *
     * @param restaurantEntity RestaurantEntity
     * @return OrdersEntities or NULL
     */
    public List<OrdersEntity> getOrdersByRestaurant(RestaurantEntity restaurantEntity) {
        try {
            List<OrdersEntity> ordersEntities = entityManager.createNamedQuery("getOrdersByRestaurant", OrdersEntity.class).setParameter("restaurant", restaurantEntity).getResultList();
            return ordersEntities;
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * get list of Orders corresponding to an Address
     *
     * @param addressEntity AddressEntity
     * @return OrdersEntities or NULL
     */
    public List<OrdersEntity> getOrdersByAddress(AddressEntity addressEntity) {
        try {
            List<OrdersEntity> ordersEntities = entityManager.createNamedQuery("getOrdersByAddress", OrdersEntity.class).setParameter("address", addressEntity).getResultList();
            return ordersEntities;
        } catch (NoResultException nre) {
            return null;
        }
    }
}
