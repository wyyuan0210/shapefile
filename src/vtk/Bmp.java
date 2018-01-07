package vtk;

public class Bmp {
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

        //读取图像
        /*vtkBMPReader bmpReader=new vtkBMPReader();
        bmpReader.SetFileName("D:\\vtk\\examples\\viewport.bmp");
        bmpReader.Update();*/

        vtkJPEGReader jpegReader=new vtkJPEGReader();
        jpegReader.SetFileName("D:\\vtk\\examples\\sheep.jpg");

        //显示图像
        /*vtkImageViewer bmpViewer=new vtkImageViewer();
        bmpViewer.SetInputData(bmpReader.GetOutput());
        bmpViewer.SetColorLevel(128);
        bmpViewer.SetColorWindow(258);
        bmpViewer.Render();*/

        //vtkImageViewer2 imageViewer=new vtkImageViewer2();
        vtkImageViewer imageViewer=new vtkImageViewer();
        imageViewer.SetInputConnection(jpegReader.GetOutputPort());
        imageViewer.SetColorLevel(128);
        imageViewer.SetColorWindow(258);
        imageViewer.Render();

        vtkRenderWindowInteractor renderWindowInteractor=new vtkRenderWindowInteractor();
        //bmpViewer.SetupInteractor(renderWindowInteractor);
        imageViewer.SetupInteractor(renderWindowInteractor);
        renderWindowInteractor.Initialize();
        renderWindowInteractor.Start();

       /* vtkJPEGWriter writer=new vtkJPEGWriter();
        writer.SetFileName("sheep1.jpg");
        writer.SetInputConnection(jpegReader.GetOutputPort());
        writer.Write();*/
    }
}
