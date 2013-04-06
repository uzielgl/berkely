/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package berkeley

Calendar calendar = Calendar.getInstance();
long l = 1296442971;
calendar.setTimeInMillis(System.currentTimeMillis());
System.out.println(calendar.getTime());
System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
System.out.println(calendar.get(Calendar.MINUTE)); 

BigDecimal b = System.currentTimeMillis() / 1000;
println b
BigDecimal a = System.currentTimeMillis() / 1000;
println a.toBigInteger()

println new Random().nextInt(100);