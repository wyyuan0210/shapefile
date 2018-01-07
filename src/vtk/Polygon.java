package vtk;

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

    public static void main (String []args)  {

        //创建坐标点
        vtkPoints points=new vtkPoints();
        points.InsertNextPoint(0.0, 0.0, 0.0);
        points.InsertNextPoint(1.0, 0.0, 0.0);
        //points.InsertNextPoint(1.0, 1.0, 0.0);
        points.InsertNextPoint(2.0, 1.0, 0.0);
        points.InsertNextPoint(0.0, 1.,0.0);

        //每两个坐标点之间分别创建一条线
        //SetId()的第一个参数是线段的端点ID，第二个参数是连接的点的ID
        /*vtkLine line0=new vtkLine();
        line0.GetPointIds().SetNumberOfIds(4);
        line0.GetPointIds().SetId(0,0);
        line0.GetPointIds().SetId(1,1);
        line0.GetPointIds().SetId(2,2);
        line0.GetPointIds().SetId(3,3);

        vtkLine line1=new vtkLine();
        line1.GetPointIds().SetId(0,1);
        line1.GetPointIds().SetId(1,3);

        vtkLine line2=new vtkLine();
        line2.GetPointIds().SetId(3,2);
        line2.GetPointIds().SetId(2,0);*/

        //面
        vtkPolygon polygon=new vtkPolygon();
        polygon.GetPointIds().SetNumberOfIds(4);
        polygon.GetPointIds().SetId(0,1);
        polygon.GetPointIds().SetId(1,2);
        polygon.GetPointIds().SetId(2,3);
        polygon.GetPointIds().SetId(3,0);
        //polygon.GetPointIds().SetId(4,4);


        /*vtkTriangle triangle=new vtkTriangle();
        triangle.GetPointIds().SetId(0,1);
        triangle.GetPointIds().SetId(1,2);
        triangle.GetPointIds().SetId(2,4);*/

        //设置拓扑结构
        vtkCellArray cellArray=new vtkCellArray();
        cellArray.InsertNextCell(4);
        double[] a;
        a=new double[4];
        for(int i=0;i<4;i++){
            a[i]=i;
            cellArray.InsertCellPoint(i);
        }
        //cellArray.InsertCellPoint(0);
        //cellArray.InsertCellPoint(1);
        //cellArray.InsertCellPoint(2);
        //cellArray.InsertCellPoint(3);

        /*vtkCellArray lines=new vtkCellArray();
        lines.InsertNextCell(line0);
        lines.InsertNextCell(line1);
        lines.InsertNextCell(line2);*/

        vtkCellArray cells=new vtkCellArray();
        //cells.InsertNextCell(triangle);
        cells.InsertNextCell(polygon);
        //cells.InsertNextCell(polygon1);

        //创建vtkPolyData类型的数据，vtkPolyData派生自vtkPointSet，
        //vtkPointSet是vtkDataSet的子类。
        //将创建的数据加入到vtkPolyData数据里
        vtkPolyData polyData=new vtkPolyData();
        polyData.SetPoints(points);
        polyData.SetVerts(cellArray);
        //polyData.SetLines(lines);
        polyData.SetPolys(cells);

        //界面
        vtkPolyDataMapper mapper=new vtkPolyDataMapper();
        mapper.SetInputData(polyData);

        vtkActor actor=new vtkActor();
        actor.SetMapper(mapper);
        actor.GetProperty().SetPointSize(4);

        vtkRenderer renderer=new vtkRenderer();
        renderer.AddActor(actor);
        renderer.SetBackground(0.1, 0.2, 0.4);

        //使用vtkTransform对vtkAxesActor进行转换，默认位于(0,0,0)
        // vtkTransform transform = new vtkTransform();
        //transform.Translate(1.0, 0.0, 0.0);
        //在同一个视口中显示坐标系，直接添加到renderer中
        // vtkAxesActor axes1 = new vtkAxesActor();
        //renderer.AddActor(axes1);
        //axes1.SetUserTransform(transform);

        vtkRenderWindow renWin=new vtkRenderWindow();
        renWin.AddRenderer(renderer);
        renWin.SetSize(600,500);

        vtkRenderWindowInteractor renderWindowInteractor=new vtkRenderWindowInteractor();
        renderWindowInteractor.SetRenderWindow(renWin);
        renWin.Render();
        renderWindowInteractor.Initialize();
        renderWindowInteractor.Start();



    }
}
