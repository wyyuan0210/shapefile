package vtk;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Feature;
import org.gdal.ogr.FeatureDefn;
import org.gdal.ogr.FieldDefn;
import org.gdal.ogr.Geometry;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;

public class Shapefile {
    public static void main (String []args)  {
        String strVectorFile ="D:\\gdal\\examples\\test\\testShap05.shp";

        // 注册所有的驱动
        ogr.RegisterAll();

        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8","NO");
        gdal.SetConfigOption("SHAPE_ENCODING","CP936");

        //创建数据
        String strDriverName = "ESRI Shapefile";
        org.gdal.ogr.Driver oDriver =ogr.GetDriverByName(strDriverName);

        // 创建数据源
        DataSource oDS = oDriver.CreateDataSource(strVectorFile,null);

        // 创建图层
        Layer oLayer =oDS.CreateLayer("TestPolygon", null, ogr.wkbPolygon, null);

        // 创建属性表
        FieldDefn oFieldID = new FieldDefn("FieldID", ogr.OFTInteger);
        oLayer.CreateField(oFieldID, 1);

        FieldDefn oFieldName = new FieldDefn("FieldName", ogr.OFTString);
        oFieldName.SetWidth(100);
        oLayer.CreateField(oFieldName, 1);

        FeatureDefn oDefn =oLayer.GetLayerDefn();

        // 创建三角形
        Feature oFeatureTriangle = new Feature(oDefn);
        oFeatureTriangle.SetField(0, 0);
        oFeatureTriangle.SetField(1, "三角形");
        Geometry geomTriangle =Geometry.CreateFromWkt("POLYGON ((0 0,20 0,10 15))");
        oFeatureTriangle.SetGeometry(geomTriangle);
        oLayer.CreateFeature(oFeatureTriangle);

        //写入文件
        oLayer.SyncToDisk();
        oDS.SyncToDisk();

        System.out.println("\n数据集创建完成！\n");
    }
}
