package Reader;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Feature;
import org.gdal.ogr.FeatureDefn;
import org.gdal.ogr.FieldDefn;
import org.gdal.ogr.Geometry;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;
import org.gdal.ogr.Driver;

public class City {
    public static void main (String []args)  {
        ogr.RegisterAll();
        String strVectorFile = "D:\\gdal\\examples\\City\\Capitals.shp";

        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8","YES");
        gdal.SetConfigOption("SHAPE_ENCODING","CP936");

        DataSource ds = ogr.Open(strVectorFile,0);

        // 获取图层
        Layer oLayer = ds.GetLayerByIndex(0);

        // 对图层进行初始化
        oLayer.ResetReading();

        // 获取图层中的属性表表头并输出
        System.out.println("属性表信息：");
        FeatureDefn oDefn =oLayer.GetLayerDefn();
        int iFieldCount =oDefn.GetFieldCount();
        for (int iAttr = 0; iAttr <iFieldCount; iAttr++)
        {
            FieldDefn oField =oDefn.GetFieldDefn(iAttr);
            System.out.println(oField.GetNameRef());
        }

        // 输出图层中的要素个数
        System.out.println("要素个数 = " + oLayer.GetFeatureCount(0));
        Feature oFeature = null;

        // 下面开始获取图层中的要素
        while ((oFeature =oLayer.GetNextFeature()) != null)
        {
            System.out.println("当前处理第" + oFeature.GetFID() + "个:\n属性值：");
            // 获取要素中的属性表内容
            for (int iField = 0; iField< iFieldCount; iField++)
            {
                FieldDefn oFieldDefn= oDefn.GetFieldDefn(iField);
                int type =oFieldDefn.GetFieldType();

                switch (type)
                {
                    case ogr.OFTString:
                    System.out.println(oFeature.GetFieldAsString(iField)+ "\t");
                    break;
                    case ogr.OFTReal:
                    System.out.println(oFeature.GetFieldAsDouble(iField)+ "\t");
                    break;
                    case ogr.OFTInteger:
                    System.out.println(oFeature.GetFieldAsInteger(iField)+ "\t");
                    break;
                    default:
                        System.out.println(oFeature.GetFieldAsString(iField)+ "\t");
                        break;
                }
            }
        }

        System.out.println("数据集关闭！");

    }
    }












