/*
 * License: GPL. Copyright 2014- (C) by Sebastian Aust (http://www.ryanthara.de/)
 *
 * This file is part of the package de.ryanthara.ja.rycon.gui
 *
 * This package is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This package is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this package. If not, see <http://www.gnu.org/licenses/>.
 */

package de.ryanthara.ja.rycon.gui;

import de.ryanthara.ja.rycon.Main;
import de.ryanthara.ja.rycon.data.I18N;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.util.Properties;


/**
 * This class implements a complete widget and it's functionality.
 * <p>
 * The GeneratorSettingsWidget of RyCON is used to set preferences for the generator
 * functionality.
 *
 * @author sebastian
 * @version 2
 * @since 1
 */
public class GeneratorSettingsWidget {

    /**
     * Member for the inner shell.
     */
    private Shell innerShell = null;

    /**
     * Member for the outer shell.
     */
    private Shell shell = null;

    /**
     * Member for the default path text field.
     */
    private Text textDefaultPath = null;

    /**
     * Member for the job path text field.
     */
    private Text textJobPath = null;

    /**
     * Member for the job path template folder text field.
     */
    private Text textJobPathTemplateFolder = null;

    /**
     * Member for the
     */
    private Text textProjectPath = null;

    /**
     * Member for the project path template folder
     */
    private Text textProjectPathTemplateFolder = null;

    /**
     * Class constructor with parameter for the outer shell object.
     * <p>
     * The user interface is initialized in a separate method, which is called from here.
     *
     * @param shell shell of the parent widget
     */
    public GeneratorSettingsWidget(Shell shell) {
        this.shell = shell;

        initUI();
    }

    /**
     * Do all the things when hitting the cancel button.
     */
    private void actionBtnCancel() {
        shell.setText(I18N.getWidgetTitleGenerator());
        innerShell.dispose();
    }

    /**
     * Do all the things when hitting the default path button.
     */
    private void actionBtnDefaultPath() {
        DirectoryDialog dlg1 = new DirectoryDialog(innerShell);

        // Set the initial filter path according to anything selected or typed in
        dlg1.setFilterPath(Main.pref.getSingleProperty("DirBase"));

        dlg1.setText(I18N.getFileChooserDirBaseTitle());

        dlg1.setMessage(I18N.getFileChooserDirBaseMessage());

        String dir1 = dlg1.open();

        if (dir1 != null) {
            textDefaultPath.setText(dir1);
        }
    }

    /**
     * Do all the things when hitting the job path button.
     */
    private void actionBtnJobPath() {
        DirectoryDialog dlg2 = new DirectoryDialog(innerShell);

        // Set the initial filter path according to anything selected or typed in
        dlg2.setFilterPath(Main.pref.getSingleProperty("DirJobs"));

        dlg2.setText(I18N.getFileChooserDirJobsTitle());

        dlg2.setMessage(I18N.getFileChooserDirJobsMessage());

        String dir2 = dlg2.open();

        if (dir2 != null) {
            textJobPath.setText(dir2);
        }
    }

    /**
     * Do all the things when hitting the template job path button.
     */
    private void actionBtnJobPathDefaultFolder() {
        DirectoryDialog dlg3 = new DirectoryDialog(innerShell);

        // Set the initial filter path according to anything selected or typed in
        dlg3.setFilterPath(Main.pref.getSingleProperty("DirJobsTemplate"));

        dlg3.setText(I18N.getFileChooserDirJobsTemplateTitle());

        dlg3.setMessage(I18N.getFileChooserDirJobsTemplateMessage());

        String dir3 = dlg3.open();

        if (dir3 != null) {
            textJobPathTemplateFolder.setText(dir3);
        }
    }

    /**
     * Do all the things when hitting the ok button.
     */
    private void actionBtnOk() {

        // checks for text field inputs and valid directories
        if ((textDefaultPath != null) && (textJobPath != null) && (textJobPathTemplateFolder != null) &&
                (textProjectPath != null) && (textProjectPathTemplateFolder != null)) {

            Properties properties = Main.pref.getProperties();

            File checkDirBase = new File(textDefaultPath.getText());
            if (!checkDirBase.exists()) {
                MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_WARNING);
                msgBox.setMessage(I18N.getMsgDirBaseNotFound());
                msgBox.setText(I18N.getMsgBoxTitleWarning());
                msgBox.open();
            } else {
                properties.setProperty("DirBase", textDefaultPath.getText());
            }

            File checkDirJobs = new File(textJobPath.getText());
            if (!checkDirJobs.exists()) {
                MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_WARNING);
                msgBox.setMessage(I18N.getMsgDirJobNotFound());
                msgBox.setText(I18N.getMsgBoxTitleWarning());
                msgBox.open();
            } else {
                properties.setProperty("DirJobs", textJobPath.getText());
            }

            File checkDirJobsDefault = new File(textJobPathTemplateFolder.getText());
            if (!checkDirJobsDefault.exists()) {
                MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_WARNING);
                msgBox.setMessage(I18N.getMsgDirJobDefaultNotFound());
                msgBox.setText(I18N.getMsgBoxTitleWarning());
                msgBox.open();
            } else {
                properties.setProperty("DirJobsTemplate", textJobPathTemplateFolder.getText());
            }

            File checkDirProject = new File(textProjectPath.getText());
            if (!checkDirProject.exists()) {
                MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_WARNING);
                msgBox.setMessage(I18N.getMsgDirProjectNotFound());
                msgBox.setText(I18N.getMsgBoxTitleWarning());
                msgBox.open();
            } else {
                properties.setProperty("DirProjects", textProjectPath.getText());
            }

            File checkDirProjectDefault = new File(textProjectPathTemplateFolder.getText());
            if (!checkDirProjectDefault.exists()) {
                MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_WARNING);
                msgBox.setMessage(I18N.getMsgDirProjectDefaultNotFound());
                msgBox.setText(I18N.getMsgBoxTitleWarning());
                msgBox.open();
            } else {
                properties.setProperty("DirProjectsTemplate", textProjectPathTemplateFolder.getText());
            }

            Main.pref.setProperties(properties);

            Main.statusBar.setStatus(I18N.getStatusSettingsSaved(), StatusBar.OK);
            shell.setText(I18N.getWidgetTitleGenerator());
            innerShell.dispose();
        } else {
            MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_WARNING);
            msgBox.setMessage(I18N.getMsgChooseDirWarning());
            msgBox.setText(I18N.getMsgBoxTitleWarning());
            msgBox.open();
        }
    }

    /**
     * Do all the things when hitting the project path button
     */
    private void actionBtnProjectPath() {
        DirectoryDialog dlg4 = new DirectoryDialog(innerShell);

        // Set the initial filter path according to anything selected or typed in
        dlg4.setFilterPath(Main.pref.getSingleProperty("DirProjects"));

        dlg4.setText(I18N.getFileChooserDirProjectsTitle());

        dlg4.setMessage(I18N.getFileChooserDirProjectsMessage());

        String dir4 = dlg4.open();

        if (dir4 != null) {
            textProjectPath.setText(dir4);
        }
    }

    /**
     * Do all the things when hitting the template project path button.
     */
    private void actionBtnProjectPathDefault() {
        DirectoryDialog dlg5 = new DirectoryDialog(innerShell);

        // Set the initial filter path according to anything selected or typed in
        dlg5.setFilterPath(Main.pref.getSingleProperty("DirProjectsTemplate"));

        dlg5.setText(I18N.getFileChooserDirProjectTemplateTitle());

        dlg5.setMessage(I18N.getFileChooserDirProjectTemplateMessage());

        String dir5 = dlg5.open();

        if (dir5 != null) {
            textProjectPathTemplateFolder.setText(dir5);
        }
    }

    /**
     * Initialize all the GUI of the generator widget.
     */
    private void initUI() {

        // golden rectangle cut with an aspect ratio of 1.618:1
        int height = Main.getRyCONWidgetHeight();
        int width = Main.getRyCONWidgetWidth();

        final int style = SWT.BORDER;

        innerShell = new Shell(shell, SWT.SHEET);
        shell.setText(I18N.getWidgetTitleGeneratorSettings());

        innerShell.addListener(SWT.Close, new Listener() {
            public void handleEvent(Event event) {
                actionBtnCancel();
            }
        });

        innerShell.setText(I18N.getWidgetTitleGeneratorSettings());
        innerShell.setSize(width, height);

        GridLayout gridLayout = new GridLayout();
        gridLayout.marginHeight = 20;
        gridLayout.numColumns = 3;
        gridLayout.makeColumnsEqualWidth = false;

        innerShell.setLayout(gridLayout);


        // default path
        Label defaultPath = new Label(innerShell, style);
        defaultPath.setText(I18N.getLabelDirBase());

        textDefaultPath = new Text(innerShell, style | SWT.SINGLE);
        textDefaultPath.setText(Main.pref.getSingleProperty("DirBase"));

        Button btnDefaultPath = new Button(innerShell, SWT.PUSH);
        btnDefaultPath.setText(I18N.getBtnChoosePath());
        btnDefaultPath.setToolTipText(I18N.getBtnChoosePath());
        btnDefaultPath.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnDefaultPath();
            }
        });

        GridData data1_1 = new GridData();
        data1_1.verticalIndent = SWT.FILL;
        defaultPath.setLayoutData(data1_1);

        GridData data1_2 = new GridData();
        data1_2.horizontalAlignment = SWT.FILL;
        data1_2.grabExcessHorizontalSpace = true;
        textDefaultPath.setLayoutData(data1_2);

        GridData data1_3 = new GridData();
        data1_3.horizontalAlignment = SWT.END;
        btnDefaultPath.setLayoutData(data1_3);


        // job path
        Label jobPath = new Label(innerShell, style);
        jobPath.setText(I18N.getLabelDirJobs());

        textJobPath = new Text(innerShell, style | SWT.SINGLE);
        textJobPath.setText(Main.pref.getSingleProperty("DirJobs"));

        Button btnJobPath = new Button(innerShell, SWT.PUSH);
        btnJobPath.setText(I18N.getBtnChoosePath());
        btnJobPath.setToolTipText(I18N.getBtnChoosePath());
        btnJobPath.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnJobPath();
            }
        });

        GridData data2_1 = new GridData();
        jobPath.setLayoutData(data2_1);

        GridData data2_2 = new GridData();
        data2_2.horizontalAlignment = SWT.FILL;
        data2_2.grabExcessHorizontalSpace = true;
        textJobPath.setLayoutData(data2_2);

        GridData data2_3 = new GridData();
        data1_3.horizontalAlignment = SWT.END;
        btnJobPath.setLayoutData(data2_3);


        // job path template folder
        Label jobPathTemplateFolder = new Label(innerShell, style);
        jobPathTemplateFolder.setText(I18N.getLabelDirJobsTemplate());

        textJobPathTemplateFolder = new Text(innerShell, style | SWT.SINGLE);
        textJobPathTemplateFolder.setText(Main.pref.getSingleProperty("DirJobsTemplate"));

        Button btnJobPathTemplateFolder = new Button(innerShell, SWT.PUSH);
        btnJobPathTemplateFolder.setText(I18N.getBtnChoosePath());
        btnJobPathTemplateFolder.setToolTipText(I18N.getBtnChoosePath());
        btnJobPathTemplateFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnJobPathDefaultFolder();
            }
        });

        GridData data3_1 = new GridData();
        jobPathTemplateFolder.setLayoutData(data3_1);

        GridData data3_2 = new GridData();
        data3_2.horizontalAlignment = SWT.FILL;
        data3_2.grabExcessHorizontalSpace = true;
        textJobPathTemplateFolder.setLayoutData(data3_2);

        GridData data3_3 = new GridData();
        data3_3.horizontalAlignment = SWT.END;
        btnJobPathTemplateFolder.setLayoutData(data3_3);


        // project path
        Label projectPath = new Label(innerShell, style);
        projectPath.setText(I18N.getLabelDirProjects());

        textProjectPath = new Text(innerShell, style | SWT.SINGLE);
        textProjectPath.setText(Main.pref.getSingleProperty("DirProjects"));

        Button btnProjectPath = new Button(innerShell, SWT.PUSH);
        btnProjectPath.setText(I18N.getBtnChoosePath());
        btnProjectPath.setToolTipText(I18N.getBtnChoosePath());
        btnProjectPath.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnProjectPath();
            }
        });

        GridData data4_1 = new GridData();
        projectPath.setLayoutData(data4_1);

        GridData data4_2 = new GridData();
        data4_2.horizontalAlignment = SWT.FILL;
        data4_2.grabExcessHorizontalSpace = true;
        textProjectPath.setLayoutData(data4_2);

        GridData data4_3 = new GridData();
        data4_3.horizontalAlignment = SWT.END;
        btnProjectPath.setLayoutData(data4_3);


        // project template path
        Label projectPathTemplateFolder = new Label(innerShell, style);
        projectPathTemplateFolder.setText(I18N.getLabelDirProjectsTemplate());

        textProjectPathTemplateFolder = new Text(innerShell, style | SWT.SINGLE);
        textProjectPathTemplateFolder.setText(Main.pref.getSingleProperty("DirProjectsTemplate"));

        Button btnProjectPatTemplateFolder = new Button(innerShell, SWT.PUSH);
        btnProjectPatTemplateFolder.setText(I18N.getBtnChoosePath());
        btnProjectPatTemplateFolder.setToolTipText(I18N.getBtnChoosePath());
        btnProjectPatTemplateFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnProjectPathDefault();
            }
        });


        GridData data5_1 = new GridData();
        projectPathTemplateFolder.setLayoutData(data5_1);

        GridData data5_2 = new GridData();
        data5_2.horizontalAlignment = SWT.FILL;
        data5_2.grabExcessHorizontalSpace = true;
        textProjectPathTemplateFolder.setLayoutData(data5_2);

        GridData data5_3 = new GridData();
        data5_3.horizontalAlignment = SWT.END;
        btnProjectPatTemplateFolder.setLayoutData(data5_3);

        // tabulator key order
        Control[] controls = new Control[]{textDefaultPath, btnDefaultPath, textJobPath, btnJobPath, textJobPathTemplateFolder,
                btnJobPathTemplateFolder, textProjectPath, btnProjectPath, textProjectPathTemplateFolder, btnProjectPatTemplateFolder};
        innerShell.setTabList(controls);


        Composite compositeCancelOK = new Composite(innerShell, SWT.NONE);
        compositeCancelOK.setLayout(new FillLayout());

        // cancel and ok button
        Button btnCancel = new Button(compositeCancelOK, SWT.NONE);
        btnCancel.setText(I18N.getBtnCancelLabel());
        btnCancel.setToolTipText(I18N.getBtnCancelLabelToolTip());
        btnCancel.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnCancel();
            }
        });

        Button btnOK = new Button(compositeCancelOK, SWT.NONE);
        btnOK.setText(I18N.getBtnOKLabel());
        btnOK.setToolTipText(I18N.getBtnOKLabelToolTip());
        btnOK.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnOk();
            }
        });


        GridData data6_1 = new GridData(SWT.END, SWT.END, false, true);
        data6_1.horizontalSpan = 3;
        compositeCancelOK.setLayoutData(data6_1);


        // innerShell.setLayout(gridLayout);
        innerShell.open();
    }

} // end of GeneratorSettingsWidget
