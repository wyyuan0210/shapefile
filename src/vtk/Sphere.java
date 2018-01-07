package vtk;
import vtk.*;

public class Sphere {
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


    public static void main (String [] args) {
        vtkSphereSource sphere = new vtkSphereSource();
        sphere.SetCenter(0.0, 0.0, 0.0);
        sphere.SetPhiResolution(100);
        sphere.SetThetaResolution(100 );
        sphere.SetRadius(5.0);
        sphere.Update();

        vtkPolyDataMapper mapper = new vtkPolyDataMapper();
        mapper.SetInputConnection(sphere.GetOutputPort());

        vtkActor actor = new vtkActor();
        actor.SetMapper(mapper);
        actor.GetProperty().SetOpacity(0.3);

        vtkRenderer rend1= new vtkRenderer();
        vtkRenderWindowInteractor renderWindowInteractor = new vtkRenderWindowInteractor();
        rend1.AddActor(actor);
        rend1.SetBackground(0.1, 0.2, 0.4);

        vtkTransform transform= new vtkTransform();
        transform.Translate(1.0, 0.0, 0.0);

        vtkAxesActor axes1=new vtkAxesActor();
        rend1.AddActor(axes1);
        axes1.SetUserTransform(transform);


        vtkRenderWindow renWin = new vtkRenderWindow();
        renWin.AddRenderer(rend1);
        renWin.SetSize(600, 500);


        renderWindowInteractor.SetRenderWindow(renWin);
        renWin.Render();
        renderWindowInteractor.Initialize();
        renderWindowInteractor.Start();

    }

}
