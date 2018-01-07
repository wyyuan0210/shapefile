package vtk;
import vtk.*;

public class Cone {

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

        vtkConeSource cone = new vtkConeSource();
        cone.SetHeight( 3.0 );
        cone.SetRadius( 1.0 );
        cone.SetResolution( 6 );

        vtkPolyDataMapper coneMapper = new vtkPolyDataMapper();
        coneMapper.SetInputConnection(cone.GetOutputPort());

        vtkActor coneActor = new vtkActor();
        coneActor.SetMapper(coneMapper);
        coneActor.GetProperty().SetColor(0.0,1.0,1.0);


        vtkRenderer ren1 = new vtkRenderer();
        ren1.AddActor(coneActor);
        ren1.SetBackground(0.1, 0.2, 0.4);

        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer( ren1 );
        renWin.SetSize(600, 500);

        ren1.ResetCamera();
        ren1.GetActiveCamera().Azimuth(90);

        vtkRenderWindowInteractor iren = new vtkRenderWindowInteractor();
        iren.SetRenderWindow(renWin);

        vtkInteractorStyleTrackballCamera style =
                new vtkInteractorStyleTrackballCamera();
        iren.SetInteractorStyle(style);
        iren.Initialize();
        iren.Start();
    }
}
