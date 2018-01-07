package vtk;
import vtk.*;

public class Newvtk {
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
    public static void main (String []args) {
        vtkCylinderSource cylinder = new vtkCylinderSource();
        cylinder.SetHeight(20.0);
        cylinder.SetCenter(0,0,0);
        cylinder.SetRadius(3.0);
        cylinder.SetResolution( 8 );

        vtkPolyDataMapper cylinderMapper = new vtkPolyDataMapper();
        cylinderMapper.SetInputConnection( cylinder.GetOutputPort() );

        vtkActor cylinderActor = new vtkActor();
        cylinderActor.SetMapper( cylinderMapper );
        cylinderActor.GetProperty().SetColor(1.0000, 0.3882, 0.2784);
        cylinderActor.RotateX(30.0);
        cylinderActor.RotateY(-45.0);

        vtkRenderer ren = new vtkRenderer();
        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer( ren );
        ren.AddActor( cylinderActor );

        vtkRenderWindowInteractor iren = new vtkRenderWindowInteractor();
        iren.SetRenderWindow(renWin);

        ren.AddActor(cylinderActor);
        ren.SetBackground(0.1, 0.2, 0.4);
        renWin.SetSize(200, 200);

        iren.Initialize();

        ren.ResetCamera();
        ren.GetActiveCamera().Zoom(1.5);
        renWin.Render();

        iren.Start();
    }
}
