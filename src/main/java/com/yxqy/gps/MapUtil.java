package com.yxqy.gps;



/**
 * 地图工具类
 *
 * @author hezhao
 * @Time 2017年7月31日 下午8:38:50
 */
public class MapUtil {

    private static double EARTH_RADIUS = 6378137;// 赤道半径(单位m)
    private final static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 转化为弧度(rad)
     * */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 基于googleMap中的算法得到两经纬度之间的距离,计算经度与谷歌地图的距离经度差不多，相差范围在0.2米以下
     *
     * @param lon1
     *            第一点的经度
     * @param lat1
     *            第一点的纬度
     * @param lon2
     *            第二点的经度
     * @param lat2
     *            第二点的纬度
     * @return 返回的距离，单位km
     * */
    public static double distance(double lon1, double lat1, double lon2,
                                  double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s / 1000;
    }

    /**
     * 基于googleMap中的算法得到两经纬度之间的距离,计算经度与谷歌地图的距离经度差不多，相差范围在0.2米以下
     *
     * @param lon1
     *            第一点的经度
     * @param lat1
     *            第一点的纬度
     * @param lon2
     *            第二点的经度
     * @param lat2
     *            第二点的纬度
     * @return 返回的距离，单位m
     * */
    public static double distance2(double lon1, double lat1, double lon2,
                                   double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }





    public static void main(String[] args) {
        // 济南国际会展中心经纬度：117.11811 36.68484
        // 趵突泉：117.00999000000002 36.66123
        //3691.0567018612664
        System.out.println(distance(116.425249, 39.914504, 116.382001, 39.913329));
        System.out.println(distance2(116.425249, 39.914504, 116.382001, 39.913329));


    }
}
