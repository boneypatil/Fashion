package com.cool.myfashion.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class DataModelParsingTest {

    private lateinit var dashboardDataNotNull: DashboardContentResult
    private lateinit var contentDataNotNull: Content
    private lateinit var imagesDataNotNull: Images


    private var dashboardDataNull: DashboardContentResult? = null
    private var contentDataNull: Content? = null
    private var imagesNull: Images? = null


    @Before
    fun setUp() {
        dashboardDataNotNull = DashboardContentResult(listOf())
        contentDataNotNull = Content(Type.Image, 2, 3, "Jackets", "url", listOf())
        imagesDataNotNull = Images("url",230,115,"jpg")
    }


    @Test
    fun `DashBoardContent success state not null`() {
        Assert.assertNotNull(dashboardDataNotNull)
    }

    @Test
    fun `DashBoardContent error state  null`() {
        Assert.assertNull(dashboardDataNull)
    }

    @Test
    fun `Content success state not null`() {
        Assert.assertNotNull(contentDataNotNull)
    }

    @Test
    fun `Content error state  null`() {
        Assert.assertNull(contentDataNull)
    }

    @Test
    fun `ImagesContent success state not null`() {
        Assert.assertNotNull(imagesDataNotNull)
    }

    @Test
    fun `ImagesContent error state  null`() {
        Assert.assertNull(imagesNull)
    }

    @Test
    fun `Content parsing  working correctly`() {

        Assert.assertEquals(Type.Image, contentDataNotNull.type)
        Assert.assertEquals(2, contentDataNotNull.cols)
        Assert.assertNotEquals(4, contentDataNotNull.cols)
        Assert.assertNotEquals("Jackets", contentDataNotNull.cols)
        Assert.assertEquals("Jackets", contentDataNotNull.title)

    }

    @Test
    fun `Images parsing  working correctly`() {

        Assert.assertNotEquals(4, imagesDataNotNull.url)
        Assert.assertNotEquals("Jackets", imagesDataNotNull.format)
        Assert.assertNotEquals(231, imagesDataNotNull.width)
        Assert.assertEquals("url", imagesDataNotNull.url)
        Assert.assertEquals(230, imagesDataNotNull.width)
        Assert.assertNotEquals(115, imagesDataNotNull.width)

    }
}