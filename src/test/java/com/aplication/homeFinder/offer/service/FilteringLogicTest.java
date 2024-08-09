package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.Offer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilteringLogicTest {

    @Mock
    private EntityManager em;
    @Mock
    private CriteriaBuilder cb;
    @Mock
    private CriteriaQuery<Offer> cr;
    @Mock
    private Root<Offer> root;
    @Mock
    private Join<Object, Object> join;
    @Mock
    private TypedQuery<Offer> typedQuery;
    @Mock
    private Path<Object> additionalInformationPath;
    @InjectMocks
    private FilteringLogic filteringLogic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        filteringLogic = new FilteringLogic(em);
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Offer.class)).thenReturn(this.cr);
        when(this.cr.from(Offer.class)).thenReturn(root);
        when(root.join("offerDetails", JoinType.INNER)).thenReturn(join);
    }

    @Test
    void testFilteringMethodWithKindOfProperty() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setKindOfProperty("MIESZKANIE");
        List<Offer> predicates = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.equal(root.get("kindOfProperty"), "MIESZKANIE")).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(predicates);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(predicates, result);

        verify(cb).equal(root.get("kindOfProperty"), "MIESZKANIE");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithValidKindOfProperty() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setKindOfProperty(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(root.get("kindOfProperty"), "MIESZKANIE");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithInvalidKindOfProperty() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setKindOfProperty("INVALID");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(root.get("kindOfProperty"), "INVALID");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinPrice() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinPrice(100000.0);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.greaterThanOrEqualTo(root.get("price"), 100000.0)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("price"), 100000.0);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMaxPrice() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMaxPrice(500000.0);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.lessThanOrEqualTo(root.get("price"), 500000.0)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).lessThanOrEqualTo(root.get("price"), 500000.0);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinAndMaxPrice() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinPrice(100000.0);
        filteringSchema.setMaxPrice(500000.0);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate minPricePredicate = mock(Predicate.class);
        Predicate maxPricePredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.greaterThanOrEqualTo(root.get("price"), 100000.0)).thenReturn(minPricePredicate);
        when(cb.lessThanOrEqualTo(root.get("price"), 500000.0)).thenReturn(maxPricePredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(minPricePredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("price"), 100000.0);
        verify(cb).lessThanOrEqualTo(root.get("price"), 500000.0);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNoPrice() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).greaterThanOrEqualTo(any(), anyDouble());
        verify(cb, never()).lessThanOrEqualTo(any(), anyDouble());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithCity() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setCity("Warsaw");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.equal(root.get("city"), "Warsaw")).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).equal(root.get("city"), "Warsaw");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithEmptyCity() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setCity("");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(root.get("city"), "");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullCity() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setCity(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(root.get("city"), null);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinNumberOfRooms() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinNumberOfRooms(2);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.greaterThanOrEqualTo(root.get("numberOfRooms"), 2)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("numberOfRooms"), 2);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMaxNumberOfRooms() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMaxNumberOfRooms(4);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.lessThanOrEqualTo(root.get("numberOfRooms"), 4)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).lessThanOrEqualTo(root.get("numberOfRooms"), 4);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinAndMaxNumberOfRooms() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinNumberOfRooms(2);
        filteringSchema.setMaxNumberOfRooms(4);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockMinPredicate = mock(Predicate.class);
        Predicate mockMaxPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.greaterThanOrEqualTo(root.get("numberOfRooms"), 2)).thenReturn(mockMinPredicate);
        when(cb.lessThanOrEqualTo(root.get("numberOfRooms"), 4)).thenReturn(mockMaxPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockMinPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("numberOfRooms"), 2);
        verify(cb).lessThanOrEqualTo(root.get("numberOfRooms"), 4);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullNumberOfRooms() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinNumberOfRooms(null);
        filteringSchema.setMaxNumberOfRooms(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).greaterThanOrEqualTo(any(), anyDouble());
        verify(cb, never()).lessThanOrEqualTo(any(), anyDouble());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinArea() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinArea(50.0);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.greaterThanOrEqualTo(root.get("area"), 50.0)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("area"), 50.0);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMaxArea() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMaxArea(100.0);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.lessThanOrEqualTo(root.get("area"), 100.0)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).lessThanOrEqualTo(root.get("area"), 100.0);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinAndMaxArea() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinArea(50.0);
        filteringSchema.setMaxArea(100.0);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockMinPredicate = mock(Predicate.class);
        Predicate mockMaxPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.greaterThanOrEqualTo(root.get("area"), 50.0)).thenReturn(mockMinPredicate);
        when(cb.lessThanOrEqualTo(root.get("area"), 100.0)).thenReturn(mockMaxPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockMinPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("area"), 50.0);
        verify(cb).lessThanOrEqualTo(root.get("area"), 100.0);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullArea() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinArea(null);
        filteringSchema.setMaxArea(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).greaterThanOrEqualTo(any(), anyDouble());
        verify(cb, never()).lessThanOrEqualTo(any(), anyDouble());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinPricePerMeter() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinPricePerMeter(5000.0);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.greaterThanOrEqualTo(root.get("pricePerMeter"), 5000.0)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("pricePerMeter"), 5000.0);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMaxPricePerMeter() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMaxPricePerMeter(10000.0);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.lessThanOrEqualTo(root.get("pricePerMeter"), 10000.0)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).lessThanOrEqualTo(root.get("pricePerMeter"), 10000.0);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinAndMaxPricePerMeter() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinPricePerMeter(5000.0);
        filteringSchema.setMaxPricePerMeter(10000.0);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockMinPredicate = mock(Predicate.class);
        Predicate mockMaxPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.greaterThanOrEqualTo(root.get("pricePerMeter"), 5000.0)).thenReturn(mockMinPredicate);
        when(cb.lessThanOrEqualTo(root.get("pricePerMeter"), 10000.0)).thenReturn(mockMaxPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockMinPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("pricePerMeter"), 5000.0);
        verify(cb).lessThanOrEqualTo(root.get("pricePerMeter"), 10000.0);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullPricePerMeter() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinPricePerMeter(null);
        filteringSchema.setMaxPricePerMeter(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).greaterThanOrEqualTo(any(), anyDouble());
        verify(cb, never()).lessThanOrEqualTo(any(), anyDouble());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinFloor() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinFloor(2);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.greaterThanOrEqualTo(root.get("floor"), 2)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("floor"), 2);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMaxFloor() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMaxFloor(5);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.lessThanOrEqualTo(root.get("floor"), 5)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).lessThanOrEqualTo(root.get("floor"), 5);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinAndMaxFloor() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinFloor(2);
        filteringSchema.setMaxFloor(5);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockMinPredicate = mock(Predicate.class);
        Predicate mockMaxPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.greaterThanOrEqualTo(root.get("floor"), 2)).thenReturn(mockMinPredicate);
        when(cb.lessThanOrEqualTo(root.get("floor"), 5)).thenReturn(mockMaxPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockMinPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("floor"), 2);
        verify(cb).lessThanOrEqualTo(root.get("floor"), 5);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullFloor() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinFloor(null);
        filteringSchema.setMaxFloor(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).greaterThanOrEqualTo(any(), anyDouble());
        verify(cb, never()).lessThanOrEqualTo(any(), anyDouble());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithOwnershipForm() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setOwnerShipForm("PELNAWLASNOSC");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate ownershipFormPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.equal(join.get("ownerShipForm"), "PELNAWLASNOSC")).thenReturn(ownershipFormPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(ownershipFormPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).equal(join.get("ownerShipForm"), "PELNAWLASNOSC");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithInvalidOwnershipForm() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setOwnerShipForm("INVALID");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(join.get("PELNAWLASNOSC"), "INVALID");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullOwnershipForm() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setOwnerShipForm(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(any(), any());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithFinishLevel() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setFinishLevel("DOWYKONCZENIA");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate finishLevelPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.equal(join.get("finishLevel"), "DOWYKONCZENIA")).thenReturn(finishLevelPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(finishLevelPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).equal(join.get("finishLevel"), "DOWYKONCZENIA");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithInvalidFinishLevel() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setFinishLevel("INVALID");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(join.get("finishLevel"), "INVALID");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullFinishLevel() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setFinishLevel(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(any(), any());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithParkingPlace() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setParkingPlace("MIEJSCEWGARAZUPODZIEMNYM");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate parkingPlacePredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.equal(join.get("parkingPlace"), "MIEJSCEWGARAZUPODZIEMNYM")).thenReturn(parkingPlacePredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(parkingPlacePredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).equal(join.get("parkingPlace"), "MIEJSCEWGARAZUPODZIEMNYM");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithInvalidParkingPlace() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setParkingPlace("INVALID");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(join.get("parkingPlace"), "INVALID");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullParkingPlace() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setParkingPlace(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(any(), any());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithHeating() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setHeating("MIEJSKIE");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate heatingPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.equal(join.get("parkingPlace"), "MIEJSKIE")).thenReturn(heatingPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(heatingPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).equal(join.get("heating"), "MIEJSKIE");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithInvalidHeating() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setHeating("INVALID");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(join.get("heating"), "INVALID");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullHeating() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setHeating(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(any(), any());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }
    @Test
    void testFilteringMethodWithMarket() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMarket("PIERWOTNY");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate marketPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(join.get("additionalInformation")).thenReturn(additionalInformationPath);
        when(cb.equal(join.get("market"), "PIERWOTNY")).thenReturn(marketPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(marketPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).equal(join.get("market"), "PIERWOTNY");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithInvalidMarket() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMarket("INVALID");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(join.get("market"), "INVALID");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullMarket() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMarket(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(any(), any());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithAnnouncerType() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setAnnouncerType("DEWELOPER");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate announcerTypetPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(join.get("additionalInformation")).thenReturn(additionalInformationPath);
        when(cb.equal(join.get("announcerType"), "DEWELOPER")).thenReturn(announcerTypetPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(announcerTypetPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).equal(join.get("announcerType"), "DEWELOPER");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithInvalidAnnouncerType() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setAnnouncerType("INVALID");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(join.get("market"), "INVALID");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullAnnouncerType() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setAnnouncerType(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(any(), any());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinYearOfConstruction() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinYearOfConstruction(2011);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(join.get("additionalInformation")).thenReturn(additionalInformationPath);
        when(cb.greaterThanOrEqualTo(root.get("yearOfConstruction"), 2011)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("yearOfConstruction"), 2011);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMaxYearOfConstruction() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMaxYearOfConstruction(2020);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(join.get("additionalInformation")).thenReturn(additionalInformationPath);
        when(cb.lessThanOrEqualTo(root.get("yearOfConstruction"), 2020)).thenReturn(mockPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).lessThanOrEqualTo(root.get("yearOfConstruction"), 2020);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithMinAndMaxYearOfConstruction() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinYearOfConstruction(2011);
        filteringSchema.setMaxYearOfConstruction(2020);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockMinPredicate = mock(Predicate.class);
        Predicate mockMaxPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(join.get("additionalInformation")).thenReturn(additionalInformationPath);
        when(cb.greaterThanOrEqualTo(root.get("yearOfConstruction"), 2011)).thenReturn(mockMinPredicate);
        when(cb.lessThanOrEqualTo(root.get("yearOfConstruction"), 2020)).thenReturn(mockMaxPredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(mockMinPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).greaterThanOrEqualTo(root.get("yearOfConstruction"), 2011);
        verify(cb).lessThanOrEqualTo(root.get("yearOfConstruction"), 2020);
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullYearOfConstruction() {
        //given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setMinYearOfConstruction(null);
        filteringSchema.setMaxYearOfConstruction(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        //when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        //then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).greaterThanOrEqualTo(any(), anyDouble());
        verify(cb, never()).lessThanOrEqualTo(any(), anyDouble());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithBuildingType() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setBuildingType("BLOK");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate buildingTypePredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(join.get("additionalInformation")).thenReturn(additionalInformationPath);
        when(cb.equal(join.get("buildingType"), "BLOK")).thenReturn(buildingTypePredicate);
        when(cb.and(any(Predicate[].class))).thenReturn(buildingTypePredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb).equal(join.get("buildingType"), "BLOK");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithInvalidBuilding() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setBuildingType("INVALID");
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(join.get("buildingType"), "INVALID");
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }

    @Test
    void testFilteringMethodWithNullBuildingType() {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setBuildingType(null);
        List<Offer> expectedOffers = new ArrayList<>();
        Predicate mockPredicate = mock(Predicate.class);

        when(em.createQuery(cr)).thenReturn(typedQuery);
        when(cb.and(any(Predicate[].class))).thenReturn(mockPredicate);
        when(typedQuery.getResultList()).thenReturn(expectedOffers);

        // when
        List<Offer> result = filteringLogic.filteringMethod(filteringSchema);

        // then
        assertNotNull(result);
        assertEquals(expectedOffers, result);

        verify(cb, never()).equal(any(), any());
        verify(cb).and(any(Predicate[].class));
        verify(em).createQuery(cr);
        verify(typedQuery).getResultList();
    }
}





