package exttabhelper.views;

import java.util.Vector;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import exttabhelper.SelectTab;

public class ButtonTabs extends ViewPart {
	public static final String ID = "exttabhelper.views.ButtonTabs";
	private Composite parent;
	private GridLayout layout;
	private Label lblView;
	private Label lblController;
	private Label lblStore;
	private Label lblModel;
	private Label lblSonstige;
	private final Vector<Button> buttons = new Vector<Button>();

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;

		this.layout = new GridLayout(1, false);
		parent.setLayout(this.layout);

		PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.addPageListener(new IPageListener() {
					@Override
					public void pageOpened(IWorkbenchPage arg0) {
						ButtonTabs.this.generateButtonTabs();
					}

					@Override
					public void pageClosed(IWorkbenchPage arg0) {
					}

					@Override
					public void pageActivated(IWorkbenchPage arg0) {
						ButtonTabs.this.generateButtonTabs();
					}
				});
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.addPartListener(new IPartListener() {
					@Override
					public void partOpened(IWorkbenchPart arg0) {
						ButtonTabs.this.generateButtonTabs();
					}

					@Override
					public void partDeactivated(IWorkbenchPart arg0) {
					}

					@Override
					public void partClosed(IWorkbenchPart arg0) {
						ButtonTabs.this.generateButtonTabs();
					}

					@Override
					public void partBroughtToTop(IWorkbenchPart arg0) {
					}

					@Override
					public void partActivated(IWorkbenchPart arg0) {
						ButtonTabs.this.generateButtonTabs();
					}
				});
		generateButtonTabs();
	}

	private void clearButtons() {
		for (Button btn : this.buttons) {
			btn.dispose();
		}

		this.buttons.clear();
	}

	private void generateButtonTabs() {
		if (this.parent.isDisposed()) {
			return;
		}
		if (PlatformUI.getWorkbench() == null)
			return;
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null)
			return;
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage() == null)
			return;
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getEditorReferences() == null) {
			return;
		}
		clearButtons();

		if (this.lblController != null)
			this.lblController.dispose();
		if (this.lblModel != null)
			this.lblModel.dispose();
		if (this.lblSonstige != null)
			this.lblSonstige.dispose();
		if (this.lblStore != null)
			this.lblStore.dispose();
		if (this.lblView != null) {
			this.lblView.dispose();
		}
		this.parent.layout(true);

		SelectTab selectTab = new SelectTab();
		selectTab.generateLists();

		if (!selectTab.viewList.isEmpty()) {
			this.lblView = new Label(this.parent, 0);
			addList(selectTab.viewList, this.lblView, "View:");
		}
		if (!selectTab.controllerList.isEmpty()) {
			this.lblController = new Label(this.parent, 0);
			addList(selectTab.controllerList, this.lblController, "Controller:");
		}
		if (!selectTab.storeList.isEmpty()) {
			this.lblStore = new Label(this.parent, 0);
			addList(selectTab.storeList, this.lblStore, "Store:");
		}
		if (!selectTab.modelList.isEmpty()) {
			this.lblModel = new Label(this.parent, 0);
			addList(selectTab.modelList, this.lblModel, "Model:");
		}
		if (!selectTab.sonstiges.isEmpty()) {
			this.lblSonstige = new Label(this.parent, 0);
			addList(selectTab.sonstiges, this.lblSonstige, "Other:");
		}
	}

	private void addList(Vector<String> list, Label lbl, String categoryText) {
		lbl.setFont(new Font(this.parent.getDisplay(), "Verdana", 8, 1));
		lbl.setText(categoryText);

		GridData gd = new GridData();
		gd.horizontalAlignment = 4;
		gd.grabExcessHorizontalSpace = true;

		lbl.setLayoutData(gd);

		for (String text : list) {
			final Button btn = new Button(this.parent, 8);
			btn.setText(text);
			gd = new GridData();
			gd.horizontalAlignment = 4;
			gd.grabExcessHorizontalSpace = true;
			btn.setLayoutData(gd);

			btn.addMouseListener(new MouseListener() {
				@Override
				public void mouseUp(MouseEvent arg0) {
					SelectTab.selectWithFilePath(btn.getText());
				}

				@Override
				public void mouseDown(MouseEvent arg0) {
				}

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
				}
			});
			this.buttons.add(btn);
		}
	}

	@Override
	public void setFocus() {
	}
}