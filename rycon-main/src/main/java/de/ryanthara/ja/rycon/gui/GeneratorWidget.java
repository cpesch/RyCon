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
import de.ryanthara.ja.rycon.io.FileUtils;
import de.ryanthara.ja.rycon.data.I18N;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.io.IOException;

/**
 * This class implements a complete widget and it's functionality.
 * <p>
 * The GeneratorWidget of RyCON is used to generate default paths and subdirectory
 * structure by a given point number.
 *
 * @author sebastian
 * @version 2
 * @since 1
 */
public class GeneratorWidget {

    /**
     * Member for the text input field.
     */
    private Text inputNumber = null;

    /**
     * Member for the inner shell.
     */
    private Shell innerShell = null;

    /**
     * Class constructor without parameters.
     * <p>
     * The user interface is initialized in a separate method, which is called from here.
     */
    public GeneratorWidget() {
        initUI();
    }

    /**
     * Do all the things when hitting the cancel button.
     */
    private void actionBtnCancel() {

        Main.setSubShellStatus(false);

        Main.statusBar.setStatus("", StatusBar.OK);

        innerShell.dispose();

    }

    /**
     * Do all the things when the OK button is hit. In this case the dialog stays open.
     */
    private void actionBtnOk() {

        String number = inputNumber.getText();

        if (number.trim().equals("")) {
            MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_WARNING);
            msgBox.setMessage(I18N.getMsgEmptyTextFieldWarning());
            msgBox.setText(I18N.getMsgEmptyTextFieldWarningJobNumber());
            msgBox.open();
        } else {
            if (generateFolders(number)) {
                Main.statusBar.setStatus(String.format(I18N.getStatusJobAndProjectGenerated(), number, number), StatusBar.OK);
            }
        }

    }

    /**
     * Do all the things when the OK and Exit button is hit. In this case the dialog is closed.
     */
    private void actionBtnOkAndExit() {

        Main.setSubShellStatus(false);

        if (!inputNumber.getText().trim().equals("")) {
            actionBtnOk();
        }
        innerShell.dispose();

    }

    /**
     * Do all the things when hitting the settings button.
     */
    private void actionBtnSettings() {
        new GeneratorSettingsWidget(innerShell);
    }

    /**
     * Controls the complete folder generation process.
     *
     * @param number job/project number as String from text field
     * @return success
     */
    private boolean generateFolders(String number) {

        boolean success = false;

        // get saved values from the properties object
        String jobDir = Main.pref.getSingleProperty("DirJobs");
        String jobDirTemplate = Main.pref.getSingleProperty("DirJobsTemplate");

        String projectDir = Main.pref.getSingleProperty("DirProjects");
        String projectDirTemplate = Main.pref.getSingleProperty("DirProjectsTemplate");


        File jobExistTest = new File(jobDir + File.separator + number);
        File projectExistsTest = new File(projectDir + File.separator + number);

        // check if folders exist
        if (!jobExistTest.exists() && !projectExistsTest.exists()) {

            /* maybe later on with java 8 support in the office
            Path copySourcePathJob = Paths.get(jobDirTemplate);
            Path copySourcePathProject = Paths.get(projectDirTemplate);

            Path copyDestinationPathJob = Paths.get(jobDir + File.separator + number);
            Path copyDestinationPathProject = Paths.get(projectDir + File.separator + number);
            */

            File copySourcePathJob = new File(jobDirTemplate);
            File copySourcePathProject = new File(projectDirTemplate);

            File copyDestinationPathJob = new File(jobDir + File.separator + number);
            File copyDestinationPathProject = new File(projectDir + File.separator + number);

            try {
                //Files.copy(copySourcePathJob, copyDestinationPathJob);
                //Files.copy(copySourcePathProject, copyDestinationPathProject);

                FileUtils fileUtils = new FileUtils();
                fileUtils.copy(copySourcePathJob, copyDestinationPathJob);
                fileUtils.copy(copySourcePathProject, copyDestinationPathProject);

                MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_INFORMATION);
                msgBox.setMessage(String.format(I18N.getMsgCreateDirJobAndProjectGenerated(), number, number));
                msgBox.setText(I18N.getMsgBoxTitleSuccess());
                msgBox.open();
                success = true;
            } catch (IOException e) {
                System.err.println(e.getMessage());

                MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_ERROR);
                msgBox.setMessage(I18N.getMsgCreateDirJobAndProjectWarning());
                msgBox.setText(I18N.getMsgBoxTitleError());
                msgBox.open();
                success = false;
            }

        } else if (jobExistTest.exists() && projectExistsTest.exists()) {
            Main.statusBar.setStatus("", StatusBar.OK);
            MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_WARNING);
            msgBox.setMessage(String.format(I18N.getMsgCreateDirJobAndProjectExist(), number, number));
            msgBox.setText(I18N.getMsgBoxTitleWarning());
            msgBox.open();
        } else if (jobExistTest.exists() && !projectExistsTest.exists()) {
            Main.statusBar.setStatus("", StatusBar.OK);
            MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_WARNING);
            msgBox.setMessage(String.format(I18N.getMsgCreateDirJobExist(), number));
            msgBox.setText(I18N.getMsgBoxTitleWarning());
            msgBox.open();
        } else if (!jobExistTest.exists() && projectExistsTest.exists()) {
            Main.statusBar.setStatus("", StatusBar.OK);
            MessageBox msgBox = new MessageBox(innerShell, SWT.ICON_WARNING);
            msgBox.setMessage(String.format(I18N.getMsgCreateDirProjectExist(), number));
            msgBox.setText(I18N.getMsgBoxTitleWarning());
            msgBox.open();
        }

        return success;

    }

    /**
     * Initialize all the GUI of the generator widget.
     */
    private void initUI() {

        // golden rectangle cut with an aspect ratio of 1.618:1
        int height = Main.getRyCONWidgetHeight();
        int width = Main.getRyCONWidgetWidth();

        innerShell = new Shell(Main.shell, SWT.CLOSE | SWT.DIALOG_TRIM | SWT.MAX | SWT.TITLE | SWT.APPLICATION_MODAL);

        innerShell.addListener(SWT.Close, new Listener() {
            public void handleEvent(Event event) {
                actionBtnCancel();
            }
        });

        FormLayout formLayout = new FormLayout();
        formLayout.marginHeight = 5;
        formLayout.marginWidth = 5;

        innerShell.setLayout(formLayout);

        innerShell.setText(I18N.getWidgetTitleGenerator());
        innerShell.setSize(width, height); // golden rectangle cut with an aspect ratio of 1.618:1

        Group groupInputField = new Group(innerShell, SWT.NONE);
        groupInputField.setText(I18N.getGroupTitleNumberInput());

        FormLayout groupInputFieldLayout = new FormLayout();
        groupInputFieldLayout.marginHeight = 5;
        groupInputFieldLayout.marginWidth = 5;
        groupInputField.setLayout(groupInputFieldLayout);

        Label jobAndProjectNumber = new Label(groupInputField, SWT.NONE);
        jobAndProjectNumber.setText(I18N.getLabelJobAndProjectNumber());

        inputNumber = new Text(groupInputField, SWT.SINGLE | SWT.BORDER);

        // platform independent key handling for ENTER, TAB, ...
        // TODO change bad listener with a better one
        inputNumber.addListener(SWT.Traverse, new Listener() {

            @Override
            public void handleEvent(Event event) {

                if ((event.stateMask & SWT.SHIFT) == SWT.SHIFT && event.detail == SWT.TRAVERSE_RETURN) {
                    actionBtnOkAndExit();
                } else if (event.detail == SWT.TRAVERSE_RETURN) {
                    actionBtnOk();
                }

            }
        });

        FormData data = new FormData();
        data.top = new FormAttachment(inputNumber, 5, SWT.CENTER);
        jobAndProjectNumber.setLayoutData(data);

        data = new FormData();
        data.left = new FormAttachment(jobAndProjectNumber, 5);
        data.right = new FormAttachment(100, 0);
        inputNumber.setLayoutData(data);

        data = new FormData();
        data.top = new FormAttachment(15, 5);
        data.left = new FormAttachment(50, -155);
        data.right = new FormAttachment(50, 155);
        groupInputField.setLayoutData(data);


        // description for the input field as text on a label
        Group groupDescription = new Group(innerShell, SWT.NONE);
        groupDescription.setText(I18N.getGroupTitleNumberInputAdvice());

        FillLayout fillLayout = new FillLayout();
        fillLayout.spacing = 5;
        fillLayout.type = SWT.VERTICAL;
        groupDescription.setLayout(fillLayout);

        Label tip = new Label(groupDescription, SWT.WRAP | SWT.BORDER | SWT.LEFT);
        tip.setText(I18N.getLabelTipGeneratorWidget());

        data = new FormData();
        data.top = new FormAttachment(groupInputField, 5);
        data.left = new FormAttachment(50, -155);
        data.right = new FormAttachment(50, 155);
        data.bottom = new FormAttachment(75, -25);
        groupDescription.setLayoutData(data);


        Button btnSettings = new Button(innerShell, SWT.NONE);
        btnSettings.setText(I18N.getBtnSettingsLabel());
        btnSettings.setToolTipText(I18N.getBtnSettingsLabelToolTip());

        btnSettings.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnSettings();
            }
        });


        Button btnCancel = new Button(innerShell, SWT.NONE);
        btnCancel.setText(I18N.getBtnCancelLabel());
        btnCancel.setToolTipText(I18N.getBtnCancelLabelToolTip());

        btnCancel.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnCancel();
            }
        });

        Button btnOK = new Button(innerShell, SWT.NONE);
        btnOK.setText(I18N.getBtnOKAndOpenLabel());
        btnOK.setToolTipText(I18N.getBtnOKAndOpenLabelToolTip());
        btnOK.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnOk();
            }
        });

        Button btnOKAndExit = new Button(innerShell, SWT.NONE);
        btnOKAndExit.setText(I18N.getBtnOKAndExitLabel());
        btnOKAndExit.setToolTipText(I18N.getBtnOKAndExitLabelToolTip());
        btnOKAndExit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                actionBtnOkAndExit();
            }
        });

        FormData dataSettings = new FormData();
        dataSettings.bottom = new FormAttachment(100, -5);
        dataSettings.left = new FormAttachment(0, 5);
        btnSettings.setLayoutData(dataSettings);

        FormData dataCancel = new FormData();
        dataCancel.bottom = new FormAttachment(100, -5);
        dataCancel.right = new FormAttachment(btnOK, 5);
        btnCancel.setLayoutData(dataCancel);

        FormData dataOk = new FormData();
        dataOk.bottom = new FormAttachment(100, -5);
        dataOk.right = new FormAttachment(btnOKAndExit, 5);
        btnOK.setLayoutData(dataOk);

        FormData dataOkAndExit = new FormData();
        dataOkAndExit.bottom = new FormAttachment(100, -5);
        dataOkAndExit.right = new FormAttachment(100, -5);
        btnOKAndExit.setLayoutData(dataOkAndExit);


        ShellCenter shellCenter = new ShellCenter(innerShell);
        innerShell.setLocation(shellCenter.centeredShellLocation());

        Main.setSubShellStatus(true);

        innerShell.open();
    }

}  // end of GeneratorWidget
