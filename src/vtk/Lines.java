package vtk;

public class Lines {
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

    public static void main(String[] args) {
        //创建坐标点
        vtkPoints points = new vtkPoints();
        points.InsertNextPoint(-138.200175592625, 55.1062335381914, 0);
        points.InsertNextPoint(-97.1747146619841, 59.9736611062335, 0);
        points.InsertNextPoint(-17.9051799824407, 48.5004389815629, 0);
        points.InsertNextPoint(-98.9130816505707, 29.3784021071115, 0);

        points.InsertNextPoint(-132.985074626866, -15.4714661984196,0);
        points.InsertNextPoint(-15.8191395961369, 15.8191395961371,0);
        points.InsertNextPoint(-71.7945566286215, -49.5434591747146,0);
        points.InsertNextPoint(-133.6804214223, -47.8050921861282,0);

        /*points.InsertNextPoint(-135.76646180860405, 33.55048287971905, 0);
        points.InsertNextPoint(-14.080772607550482, 60.66900790166813, 0);
        points.InsertNextPoint(-122.55487269534679, -9.21334503950834, 0);
        points.InsertNextPoint(-122.55487269534679, -91.61194029850746, 0);*/

        /*vtkLine line1=new vtkLine();
        line1.GetPointIds().SetId(0, 0);
        line1.GetPointIds().SetId(1, 1);

        vtkLine line2=new vtkLine();
        //line2.GetPointIds().SetNumberOfIds(2);
        line2.GetPointIds().SetId(0, 2);
        line2.GetPointIds().SetId(1, 3);*/

        //面
        vtkPolygon polygon1=new vtkPolygon();
        polygon1.GetPointIds().SetNumberOfIds(4);
        polygon1.GetPointIds().SetId(0,1);
        polygon1.GetPointIds().SetId(1,2);
        polygon1.GetPointIds().SetId(2,3);
        polygon1.GetPointIds().SetId(3,0);

        vtkPolygon polygon2=new vtkPolygon();
        polygon2.GetPointIds().SetNumberOfIds(8);
        polygon2.GetPointIds().SetId(0,4);
        polygon2.GetPointIds().SetId(1,5);
        polygon2.GetPointIds().SetId(2,6);
        polygon2.GetPointIds().SetId(3,7);
        polygon2.GetPointIds().SetId(4,4);

        //设置拓扑结构
        vtkCellArray cellArray = new vtkCellArray();
        cellArray.InsertNextCell(8);
        double[] a;
        a = new double[8];
        for (int i = 0; i < 8; i++) {
            a[i] = i;
            cellArray.InsertCellPoint(i);
        }

        /*vtkLine line=new vtkLine();
        vtkCellArray lines1 = new vtkCellArray();
        //lines1.InsertNextCell(line1);
        //lines1.InsertNextCell(line2);
        for (int i=0;i<4;i=i+2)
        {
            line.GetPointIds().SetId(0,i);
            line.GetPointIds().SetId(1,i+1);
            lines1.InsertNextCell(line);
        }*/

        vtkCellArray cells=new vtkCellArray();
        cells.InsertNextCell(polygon1);
        cells.InsertNextCell(polygon2);

        /*vtkCellArray cells1=new vtkCellArray();
        cells1.InsertNextCell(polygon2);*/


        //将创建的数据加入到vtkPolyData数据里
        vtkPolyData polyData = new vtkPolyData();
        polyData.SetPoints(points);
        polyData.SetVerts(cellArray);
        //polyData.SetLines(lines1);
        polyData.SetPolys(cells);

        /*vtkPolyData polyData1=new vtkPolyData();
        polyData1.SetPoints(points);
        polyData1.SetVerts(cellArray);
        polyData1.SetPolys(cells1);*/

        //界面
        vtkPolyDataMapper mapper = new vtkPolyDataMapper();
        mapper.SetInputData(polyData);
        /*vtkPolyDataMapper mapper1=new vtkPolyDataMapper();
        mapper1.SetInputData(polyData1);*/

        vtkActor actor = new vtkActor();
        actor.SetMapper(mapper);
        actor.GetProperty().SetPointSize(4);
        /*vtkActor actor1=new vtkActor();
        actor1.SetMapper(mapper1);
        actor1.GetProperty().SetPointSize(4);*/

        vtkRenderer renderer = new vtkRenderer();
        renderer.AddActor(actor);
        //renderer.AddActor(actor1);
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
}
