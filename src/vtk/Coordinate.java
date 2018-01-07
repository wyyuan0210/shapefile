package vtk;

public class Coordinate {
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
        //创建一个球体
        vtkSphereSource sphereSource = new vtkSphereSource();
        sphereSource.SetCenter(0.0, 0.0, 0.0);
        sphereSource.SetRadius(1.0);
        sphereSource.Update();

        vtkPolyDataMapper mapper = new vtkPolyDataMapper();
        mapper.SetInputConnection(sphereSource.GetOutputPort());

        vtkActor actor = new vtkActor();
        actor.SetMapper(mapper);
        //为了能够看清楚vtkAxesActor，特意设置的透明度
        actor.GetProperty().SetOpacity(0.3);

        vtkRenderer renderer = new vtkRenderer();
        vtkRenderWindow renderWindow = new vtkRenderWindow();
        renderWindow.AddRenderer(renderer);

        vtkRenderWindowInteractor renderWindowInteractor = new vtkRenderWindowInteractor();
        renderWindowInteractor.SetRenderWindow(renderWindow);
        renderer.AddActor(actor);
        renderer.SetBackground(.2, .3, .4);

        //使用vtkTransform对vtkAxesActor进行转换，默认位于(0,0,0)
        vtkTransform transform = new vtkTransform();
        transform.Translate(1.0, 0.0, 0.0);

        //1、在同一个视口中显示坐标系，直接添加到renderer中
        vtkAxesActor axes1 = new vtkAxesActor();
        renderer.AddActor(axes1);
        axes1.SetUserTransform(transform);
        vtkAxesActor axes = new vtkAxesActor();
        //2、以Widget方式,在左下角的视口中显示坐标系，可进行鼠标交互
        vtkOrientationMarkerWidget widget = new vtkOrientationMarkerWidget();
        widget.SetOutlineColor(0.9300, 0.5700, 0.1300);
        widget.SetOrientationMarker(axes);
        widget.SetInteractor(renderWindowInteractor);
        widget.SetViewport(0.0, 0.0, 0.4, 0.4);
        widget.SetEnabled(1);
        widget.InteractiveOn();

        renderer.ResetCamera();
        renderWindow.Render();

        // Begin mouse interaction
        renderWindowInteractor.Start();
    }
}
