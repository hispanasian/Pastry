package com.pastry

import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import akka.actor.ActorRef
import org.scalatest.junit.AssertionsForJUnit
import java.lang.ArrayIndexOutOfBoundsException

/**
 * Tests com.bitcoin.ShaHasher
 */
class RoutingTableTest extends AssertionsForJUnit 
{
  var test:RoutingTable[String] = null
  var value:BaseNValue = _
  var root:String = _
  var n:Int = _
  
  @Before
  def initialize() = 
  {
    value = new BaseNValue(19410,4) /* 19410 = 10233102 in base 4 */
    root = new String("boss")
    n = Math.pow(Math.pow(2, 4), 8).toInt
    test = new RoutingTable[String](value, n = n, b = 2, owner = root)
  }
  
  @Test
  def makeTableTest =
  {
    var table = test.makeTable(5,10)
    assertEquals(5, table.size)
    assertEquals(10, table(0).size)
    assertEquals(10, table(1).size)
    assertEquals(10, table(2).size)
    assertEquals(10, table(3).size)
    assertEquals(null, table(0)(0))
    
    try 
    { 
      assert(null, table(5))
      fail("Accessing 5 should have thrown an exception")
    }
    catch 
    {
      case e:ArrayIndexOutOfBoundsException =>
      case _:Throwable => fail("This should have thrown an ArrayOutOfBoundsException")
    }
  }
  
  @Test
  def makeRoutingTableTest =
  {
    var temp = new String("not an actorref")
    value = new BaseNValue(19410,4) /* 19410 = 10233102 in base 4 (8 digits) */
    var table = test.makeRoutingTable(8,4, value, temp)
    assertEquals(temp, table(0)(1).node)
    assertEquals(temp, table(1)(0).node)
    assertEquals(temp, table(2)(2).node)
    assertEquals(temp, table(3)(3).node)
    assertEquals(temp, table(4)(3).node)
    assertEquals(temp, table(5)(1).node)
    assertEquals(temp, table(6)(0).node)
    assertEquals(temp, table(7)(2).node)
    
    value = new BaseNValue(7122,4) /* 19410 = 1233102 in base 4 (7 digits) */
    table = test.makeRoutingTable(8,4, value, temp)
    assertEquals(temp, table(0)(0).node)
    assertEquals(temp, table(1)(1).node)
    assertEquals(temp, table(2)(2).node)
    assertEquals(temp, table(3)(3).node)
    assertEquals(temp, table(4)(3).node)
    assertEquals(temp, table(5)(1).node)
    assertEquals(temp, table(6)(0).node)
    assertEquals(temp, table(7)(2).node)
    
    value = new BaseNValue(65538,4) /* 65538 = 100000002 in base 4 (9 digits) */
    table = test.makeRoutingTable(8,4, value, temp)    
    assertEquals(temp, table(0)(0).node)
    assertEquals(temp, table(1)(0).node)
    assertEquals(temp, table(2)(0).node)
    assertEquals(temp, table(3)(0).node)
    assertEquals(temp, table(4)(0).node)
    assertEquals(temp, table(5)(0).node)
    assertEquals(temp, table(6)(0).node)
    assertEquals(temp, table(7)(2).node)
  }
}