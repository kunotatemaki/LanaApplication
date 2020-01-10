package com.raul.androidapps.lanaapplication.domain

import com.nhaarman.mockitokotlin2.whenever
import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.resources.ResourcesManager
import com.raul.androidapps.lanaapplication.utils.cfoDiscount
import com.raul.androidapps.lanaapplication.utils.marketingDiscount
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class DiscountsHelperKtTest {

    private val pen = Product("PEN", "Lana Voucher", 5.0)
    private val tshirt = Product("TSHIRT", "Lana T-Shirt", 20.0)
    private val mug = Product("MUG", "Lana Coffee Mug", 7.5)
    private val products = listOf(pen, tshirt, mug)

    private val marketingDescription = "marketing description"
    private val cfoDescription = "cfo description"

    @Mock
    lateinit var resourcesManager: ResourcesManager

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(resourcesManager.getString(R.string.marketing_discount_description))
            .thenReturn(
                marketingDescription
            )
        whenever(resourcesManager.getString(R.string.cfo_discount_description))
            .thenReturn(
                cfoDescription
            )
    }

    @Test
    fun marketingDiscountOdd() {
        products.first { it.code == "PEN" }.timesInBasket = 5
        val discount = marketingDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, marketingDescription)
        assertEquals(discount.savedMoney, 10.0, 0.009)
    }

    @Test
    fun marketingDiscountEven() {
        products.first { it.code == "PEN" }.timesInBasket = 4
        val discount = marketingDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, marketingDescription)
        assertEquals(discount.savedMoney, 10.0, 0.009)
    }

    @Test
    fun marketingDiscountLessThan2() {
        products.first { it.code == "PEN" }.timesInBasket = 1
        val discount = marketingDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, marketingDescription)
        assertEquals(discount.savedMoney, 0.0, 0.009)
    }

    @Test
    fun marketingDiscountEmptyList() {
        products.first { it.code == "PEN" }.timesInBasket = 0
        val discount = marketingDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, marketingDescription)
        assertEquals(discount.savedMoney, 0.0, 0.009)
    }

    @Test
    fun cfoDiscount3Tshirts() {
        products.first { it.code == "TSHIRT" }.timesInBasket = 3
        val discount = cfoDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, cfoDescription)
        assertEquals(discount.savedMoney, 3.0, 0.009)
    }

    @Test
    fun cfoDiscountMoreThan3Tshirts() {
        products.first { it.code == "TSHIRT" }.timesInBasket = 7
        val discount = cfoDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, cfoDescription)
        assertEquals(discount.savedMoney, 7.0, 0.009)
    }

    @Test
    fun cfoDiscount2Tshirts() {
        products.first { it.code == "TSHIRT" }.timesInBasket = 2
        val discount = cfoDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, cfoDescription)
        assertEquals(discount.savedMoney, 0.0, 0.009)
    }

    @Test
    fun cfoDiscount0Tshirts() {
        products.first { it.code == "TSHIRT" }.timesInBasket = 0
        val discount = cfoDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, cfoDescription)
        assertEquals(discount.savedMoney, 0.0, 0.009)
    }



}