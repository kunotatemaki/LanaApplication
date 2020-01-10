package com.raul.androidapps.lanaapplication.domain

import com.nhaarman.mockitokotlin2.whenever
import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.resources.ResourcesManager
import com.raul.androidapps.lanaapplication.utils.marketingDiscount
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class DiscountsHelperKtTest {

    val pen = Product("PEN", "Lana Voucher", 5.0)
    val tshirt = Product("TSHIRT", "Lana T-Shirt", 20.0)
    val mug = Product("MUG", "Lana Coffee Mug", 7.5)

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
    fun calculateDiscounts() {
    }

    @Test
    fun marketingDiscountOdd() {
        val products = listOf(pen, pen, pen, pen, pen)
        val discount = marketingDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, marketingDescription)
        assertEquals(discount.savedMoney, 10.0, 0.009)
    }

    @Test
    fun marketingDiscountEven() {
        val products = listOf(pen, pen, pen, pen)
        val discount = marketingDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, marketingDescription)
        assertEquals(discount.savedMoney, 10.0, 0.009)
    }

    @Test
    fun marketingDiscountLessThan2() {
        val products = listOf(pen)
        val discount = marketingDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, marketingDescription)
        assertEquals(discount.savedMoney, 0.0, 0.009)
    }

    @Test
    fun marketingDiscountEmptyList() {
        val products = listOf<Product>()
        val discount = marketingDiscount(products, resourcesManager)
        assertNotNull(discount)
        assertEquals(discount.description, marketingDescription)
        assertEquals(discount.savedMoney, 0.0, 0.009)
    }

    @Test
    fun cfoDiscount() {
    }
}