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
import vtk.*;
import java.io.*;
import java.awt.*;


public class Polygon {

    static {
        if (!vtkNativeLibrary.LoadAllNativeLibraries()) {
            for (vtkNativeLibrary lib : vtkNativeLibrary.values()) {
                if (!lib.IsLoaded()) {
                    System.out.println(lib.GetLibraryName() + " not loaded");
                }
            }
        }
        vtkNativeLibrary.DisableOutputWindow(null);
    }

    static Double[] ptx = new Double[100];
    static Double[] pty = new Double[100];
    static int count = 0;
    static int getCount=0;


    public static void read() {
        ogr.RegisterAll();
        String strVectorFile = "D:\\gdal\\examples\\province\\CN_PSim.shp";

        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
        gdal.SetConfigOption("SHAPE_ENCODING", "CP936");

        DataSource ds = ogr.Open(strVectorFile, 0);
        if (ds == null) {
            System.out.println("打开文件【" + strVectorFile + "】失败！");
            return;
        }
        System.out.println("打开文件【" + strVectorFile + "】成功！");

        // 获取第一个图层
        Layer oLayer = ds.GetLayerByIndex(0);
        // 对图层进行初始化
        oLayer.ResetReading();

        // 获取图层中的属性表表头并输出
        /*System.out.println("属性表结构信息：");
        FeatureDefn oDefn = oLayer.GetLayerDefn();
        int iFieldCount = oDefn.GetFieldCount();
        for (int iAttr = 0; iAttr < iFieldCount; iAttr++) {
            FieldDefn oField = oDefn.GetFieldDefn(iAttr);
            System.out.println(oField.GetNameRef());
        }*/

        // 输出图层中的要素个数
        System.out.println("要素个数 = " + oLayer.GetFeatureCount(0));
        Feature oFeature = null;

        // 下面开始获取图层中的要素
        /*try {
            File wr = new File("D:\\gdal\\examples\\lines.txt");
            wr.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(wr));*/

        vtkPoints p = new vtkPoints();
        vtkCellArray lines = new vtkCellArray();
        vtkCellArray polygons=new vtkCellArray();
        while ((oFeature = oLayer.GetNextFeature()) != null) {
            System.out.println("当前处理第" + oFeature.GetFID() + "个:\n属性值：");
            // 获取要素中的属性表内容
            /*for (int iField = 0; iField < iFieldCount; iField++) {
                FieldDefn oFieldDefn = oDefn.GetFieldDefn(iField);
                int type = oFieldDefn.GetFieldType();

                switch (type) {
                    case ogr.OFTString:
                        System.out.println(oFeature.GetFieldAsString(iField) + "\t");
                        break;
                    case ogr.OFTReal:
                        System.out.println(oFeature.GetFieldAsDouble(iField) + "\t");
                        break;
                    case ogr.OFTInteger:
                        System.out.println(oFeature.GetFieldAsInteger(iField) + "\t");
                        break;
                    default:
                        System.out.println(oFeature.GetFieldAsString(iField) + "\t");
                        break;
                }
            }*/
            // 获取要素中的几何信息
            Geometry oGeometry = oFeature.GetGeometryRef();
            if (oGeometry != null) {
                if (oGeometry.GetGeometryType() == ogr.wkbPoint) {
                    System.out.println("点数据坐标：\n" + oGeometry.GetX() + " " + oGeometry.GetY());
                    ptx[count] = oGeometry.GetX();
                    pty[count] = oGeometry.GetY();
                    count++;
                        /*out.write(String.valueOf(oGeometry.GetX()));
                        out.write("  ");
                        out.write(String.valueOf(oGeometry.GetY()));
                        out.write("  ");
                        out.write("0.0");
                        out.write("\r\n");*/
                } else if (oGeometry.GetGeometryType() == ogr.wkbLineString) {
                    double longs;
                    longs = oGeometry.Length();
                    System.out.println("长度：" + " " + longs);
                    vtkPolyLine pL = new vtkPolyLine();
                    pL.GetPointIds().SetNumberOfIds(oGeometry.GetPointCount());
                    for (int i = 0; i <oGeometry.GetPointCount(); i++) {
                        /*double x=oGeometry.GetX(i);
                        double y=oGeometry.GetY(i);
                        System.out.println("节点:"+x+" "+y);*/
                        p.InsertNextPoint(oGeometry.GetX(i), oGeometry.GetY(i), 0);
                        count++;
                        pL.GetPointIds().SetId(i, i);
                    }
                    lines.InsertNextCell(pL);
                    //line(p,lines);
                    //vtkread(p);
                }
                else if (oGeometry.GetGeometryType()==ogr.wkbPolygon){
                    double area;
                    area=oGeometry.GetArea();
                    System.out.println("面积："+" "+area);
                    //System.out.println(oGeometry.ExportToWkt());
                    //System.out.println(oGeometry.GetBoundary().GetPointCount());

                    //vtkPolygon Ply=new vtkPolygon();
                    //Ply.GetPointIds().SetNumberOfIds(oGeometry.GetBoundary().GetPointCount());
                    vtkPolyLine pl=new vtkPolyLine();
                    pl.GetPointIds().SetNumberOfIds(oGeometry.GetBoundary().GetPointCount());
                    int i=getCount;
                    for (int j=0;j<oGeometry.GetBoundary().GetPointCount();j++){
                        /*double x = oGeometry.GetBoundary().GetX(j);
                        double y = oGeometry.GetBoundary().GetY(j);
                        System.out.println(x+"  "+y);*/
                        p.InsertNextPoint(oGeometry.GetBoundary().GetX(j),oGeometry.GetBoundary().GetY(j),0);
                        //Ply.GetPointIds().SetId(j,getCount++);
                        pl.GetPointIds().SetId(j,getCount++);
                   }
                    //Ply.GetPointIds().SetId(oGeometry.GetBoundary().GetPointCount(),i);
                    //pl.GetPointIds().SetId(oGeometry.GetBoundary().GetPointCount(),i);
                    lines.InsertNextCell(pl);
                    //polygons.InsertNextCell(Ply);
                    //line(p,lines);
                    //vtkread(p);
                    //polygon(p,polygons);
                }

                else if (oGeometry.GetGeometryType() == ogr.wkbMultiPolygon) {
                    double area;
                    area = oGeometry.GetArea();
                    System.out.println("面积：" + " " + area);
                    System.out.println(oGeometry.GetGeometryCount());
                    //System.out.println(oGeometry.GetGeometryType());
                    for (int i=0;i<oGeometry.GetGeometryCount();i++){
                        Geometry geo = oGeometry.GetGeometryRef(i);
                        //vtkPolygon ply=new vtkPolygon();
                        //ply.GetPointIds().SetNumberOfIds(geo.GetBoundary().GetPointCount());
                        vtkPolyLine PL = new vtkPolyLine();
                        PL.GetPointIds().SetNumberOfIds(geo.GetBoundary().GetPointCount());
                        int k=count;
                        for (int j=0;j<geo.GetBoundary().GetPointCount();j++){
                        /*double x = oGeometry.GetBoundary().GetX(j);
                        double y = oGeometry.GetBoundary().GetY(j);
                        System.out.println(x+"  "+y);*/
                            p.InsertNextPoint(geo.GetBoundary().GetX(j),geo.GetBoundary().GetY(j),0);
                            //count++;
                            //ply.GetPointIds().SetId(j,count++);
                            PL.GetPointIds().SetId(j,count++);
                        }
                        //ply.GetPointIds().SetId(geo.GetBoundary().GetPointCount(),k);
                        lines.InsertNextCell(PL);
                        //polygons.InsertNextCell(ply);
                    }
                    //line(p,lines);
                    //polygon(p,polygons);
                }
            //System.out.println(count);
        }
            else{
                System.out.println("没有几何要素\n");
            }
            //break;
        }
            /*out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }*/

        System.out.println("数据集关闭！");
        //vtkread(p);
        line(p,lines);
        //polygon(p,polygons);
    }

    public static void line(vtkPoints pt,vtkCellArray lines){

        /*vtkCellArray ca=new vtkCellArray();
        ca.InsertNextCell(count);
        double[] a;
        a = new double[count];
        for (int i = 0; i < count; i++) {
            a[i] = i;
            ca.InsertCellPoint(i);
        }*/

        vtkPolyData polyData = new vtkPolyData();
        polyData.SetPoints(pt);
        ///polyData.SetVerts(ca);
        polyData.SetLines(lines);

        //界面
        vtkPolyDataMapper mapper = new vtkPolyDataMapper();
        mapper.SetInputData(polyData);

        vtkActor actor = new vtkActor();
        actor.SetMapper(mapper);
        actor.GetProperty().SetPointSize(4);

        vtkRenderer renderer = new vtkRenderer();
        renderer.AddActor(actor);
        renderer.SetBackground(0.1, 0.2, 0.4);

        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer(renderer);
        renWin.SetSize(600, 500);

        vtkRenderWindowInteractor renderWindowInteractor = new vtkRenderWindowInteractor();
        renderWindowInteractor.SetRenderWindow(renWin);
        renWin.Render();
        renderWindowInteractor.Initialize();
        renderWindowInteractor.Start();
    }

    public static void vtkread(vtkPoints p) {
        /*vtkPoints points = new vtkPoints();
        for (int i=0;i<count;i++){
            points.InsertNextPoint(ptx[i], pty[i], 0);
        }*/

        //设置拓扑结构
        vtkCellArray cellArray = new vtkCellArray();
        cellArray.InsertNextCell(count+getCount);
        double[] a;
        a = new double[count+getCount];
        for (int i = 0; i < count+getCount; i++) {
            a[i] = i;
            cellArray.InsertCellPoint(i);
        }

        //将创建的数据加入到vtkPolyData数据里
        vtkPolyData polyData = new vtkPolyData();
        polyData.SetPoints(p);
        polyData.SetVerts(cellArray);

        //界面
        vtkPolyDataMapper mapper = new vtkPolyDataMapper();
        mapper.SetInputData(polyData);

        vtkActor actor = new vtkActor();
        actor.SetMapper(mapper);
        actor.GetProperty().SetPointSize(4);

        vtkRenderer renderer = new vtkRenderer();
        renderer.AddActor(actor);
        renderer.SetBackground(0.1, 0.2, 0.4);

        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer(renderer);
        renWin.SetSize(600, 500);

        vtkRenderWindowInteractor renderWindowInteractor = new vtkRenderWindowInteractor();
        renderWindowInteractor.SetRenderWindow(renWin);
        renWin.Render();
        renderWindowInteractor.Initialize();
        renderWindowInteractor.Start();
    }

    public static void polygon(vtkPoints pt,vtkCellArray polygons){
        /*vtkCellArray ca=new vtkCellArray();
        ca.InsertNextCell(count+getCount);
        double[] a;
        a = new double[count+getCount];
        for (int i = 0; i < count+getCount; i++) {
            a[i] = i;
            ca.InsertCellPoint(i);
        }*/

        vtkPolyData polyData = new vtkPolyData();
        polyData.SetPoints(pt);
        //polyData.SetVerts(ca);
        //polyData.SetLines(lines);
        polyData.SetPolys(polygons);

        //界面
        vtkPolyDataMapper mapper = new vtkPolyDataMapper();
        mapper.SetInputData(polyData);

        vtkActor actor = new vtkActor();
        actor.SetMapper(mapper);
        actor.GetProperty().SetPointSize(4);

        vtkRenderer renderer = new vtkRenderer();
        renderer.AddActor(actor);
        renderer.SetBackground(0.1, 0.2, 0.4);

        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer(renderer);
        renWin.SetSize(600, 500);

        vtkRenderWindowInteractor renderWindowInteractor = new vtkRenderWindowInteractor();
        renderWindowInteractor.SetRenderWindow(renWin);
        renWin.Render();
        renderWindowInteractor.Initialize();
        renderWindowInteractor.Start();
    }

    public static void main(String[] args) {
        read();
        /*vtkPoints pt=new vtkPoints();
        pt.InsertNextPoint(0,1,0);
        pt.InsertNextPoint(1,0,0);
        pt.InsertNextPoint(2,0,0);
        pt.InsertNextPoint(3,1,0);
        pt.InsertNextPoint(2,2,0);
        pt.InsertNextPoint(0,2,0);
        pt.InsertNextPoint(2,1,0);

        vtkPolygon polygon=new vtkPolygon();
        polygon.GetPointIds().SetNumberOfIds(7);
        polygon.GetPointIds().SetId(0,1);
        polygon.GetPointIds().SetId(1,2);
        polygon.GetPointIds().SetId(2,0);

        vtkPolygon polygon1=new vtkPolygon();
        polygon1.GetPointIds().SetNumberOfIds(7);
        polygon1.GetPointIds().SetId(0,3);
        polygon1.GetPointIds().SetId(1,4);
        polygon1.GetPointIds().SetId(2,5);
        polygon1.GetPointIds().SetId(3,6);
        polygon1.GetPointIds().SetId(4,3);

        vtkCellArray cell=new vtkCellArray();
        cell.InsertNextCell(polygon);
        cell.InsertNextCell(polygon1);
        polygon(pt,cell);*/

        //vtkLine pL = new vtkLine();
        //vtkLine pL1 = new vtkLine();
        /*vtkPolyLine pL=new vtkPolyLine();
        pL.GetPointIds().SetNumberOfIds(3);
        pL.GetPointIds().SetId(0,0);
        pL.GetPointIds().SetId(1,1);
        pL.GetPointIds().SetId(2,2);
        pL.GetPointIds().SetId(2,0);
        /*for (int i=0;i<3;i++) {
            pL.GetPointIds().SetId(i,i);
            pL.GetPointIds().SetId(0,0);
        }
        vtkCellArray lines=new vtkCellArray();
        lines.InsertNextCell(pL);
        line(pt,lines);*/

    }


}

