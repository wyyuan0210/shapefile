package vtk;

public class Dem {
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
        vtkDEMReader reader=new vtkDEMReader();
        reader.SetFileName("D:\\vtk\\examples\\cn_dem");
        reader.Update();

        vtkLookupTable lut= new vtkLookupTable();
        lut.SetHueRange(0.6, 0);
        lut.SetSaturationRange(1.0, 0);
        lut.SetValueRange(0.5, 1.0);
        lut.SetTableRange(reader.GetOutput().GetScalarRange());

        //visualize
        vtkImageMapToColors mapToColors=new vtkImageMapToColors();
        mapToColors.SetLookupTable(lut);
        mapToColors.SetInputConnection(reader.GetOutputPort());

        vtkImageActor actor=new vtkImageActor();
        actor.GetMapper().SetInputConnection(mapToColors.GetOutputPort());


        //界面
        /*vtkPolyDataMapper mapper=new vtkPolyDataMapper();
        mapper.SetInputData(polyData);

        vtkActor actor=new vtkActor();
        actor.SetMapper(mapper);
        actor.GetProperty().SetPointSize(4);*/

        vtkRenderer renderer=new vtkRenderer();
        renderer.AddActor(actor);
        renderer.ResetCamera();

        vtkRenderWindow renWin=new vtkRenderWindow();
        renWin.AddRenderer(renderer);
        renWin.SetSize(600,500);

        vtkRenderWindowInteractor renderWindowInteractor=new vtkRenderWindowInteractor();
        vtkInteractorStyleImage style=new vtkInteractorStyleImage();
        renderWindowInteractor.SetInteractorStyle(style);
        renderWindowInteractor.SetRenderWindow(renWin);
        renderWindowInteractor.Initialize();
        renderWindowInteractor.Start();

    }
}
