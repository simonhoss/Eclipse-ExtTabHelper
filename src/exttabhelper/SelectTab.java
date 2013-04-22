package exttabhelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.ResourceUtil;

public class SelectTab {
	public Vector<String> viewList = new Vector<String>();
	public Vector<String> controllerList = new Vector<String>();
	public Vector<String> storeList = new Vector<String>();
	public Vector<String> modelList = new Vector<String>();
	public Vector<String> sonstiges = new Vector<String>();

	public static void selectWithFilePath(String filepath) {
		IEditorReference[] arrayOfIEditorReference;
		int j = (arrayOfIEditorReference = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences()).length;
		for (int i = 0; i < j; i++) {
			IEditorReference editor = arrayOfIEditorReference[i];
			try {
				IFile file = ResourceUtil.getFile(editor.getEditorInput());
				IPath path = file.getFullPath();
				String pathString = path.toOSString();

				if (filepath.equals(pathString))
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().activate(editor.getPart(true));
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}

	public void generateLists() {
		IEditorReference[] arrayOfIEditorReference;
		int j = (arrayOfIEditorReference = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences()).length;
		for (int i = 0; i < j; i++) {
			IEditorReference editor = arrayOfIEditorReference[i];
			try {
				IFile file = ResourceUtil.getFile(editor.getEditorInput());
				if(file == null)
					continue;
				IPath path = file.getFullPath();
				String pathString = path.toOSString();

				if (pathString.contains("view"))
					this.viewList.add(pathString);
				else if (pathString.contains("controller"))
					this.controllerList.add(pathString);
				else if (pathString.contains("model"))
					this.modelList.add(pathString);
				else if (pathString.contains("store"))
					this.storeList.add(pathString);
				else
					this.sonstiges.add(pathString);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
		sort();
	}

	protected void sort() {
		sortList(this.viewList);
		sortList(this.controllerList);
		sortList(this.storeList);
		sortList(this.modelList);
		sortList(this.sonstiges);
	}

	private void sortList(Vector<String> list) {

		Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
	}
}