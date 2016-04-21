package hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.dialog.addemployee;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.component.composite.FieldsPanel;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.component.composite.OkCancelButtonBar;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.component.composite.OkCancelButtonBar.OkCancelButtonBarListener;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.component.composite.ValidationErrorMessagesLabel;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.component.field.IntegerField;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.dialog.DefaultModalDialog;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.dialog.addemployee.typespecific.CommissionedEmployeeFieldsPanel;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.dialog.addemployee.typespecific.EmployeeFieldsPanel;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.dialog.addemployee.typespecific.HourlyEmployeeFieldsPanel;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.impl.swing.viewimpl.dialog.addemployee.typespecific.SalariedEmployeeFieldsPanel;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.views_and_controllers.common.validation.ValidationErrorMessagesModel;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.views_and_controllers.dialog.addemployee.AddEmployeeView;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.adapters.primary.ui.views_and_controllers.dialog.addemployee.AddEmployeeView.AddEmployeeViewListener;

public class AddEmployeeDialog extends DefaultModalDialog<AddEmployeeViewListener> implements AddEmployeeView {

	private final FieldsPanel fieldsPanel = new FieldsPanel();
	private ValidationErrorMessagesLabel errorMessageLabel;
	private IntegerField ifEmployeeId = new IntegerField();
	private JTextField tfName = new JTextField();
	private JTextField tfAddress = new JTextField();
	private JComboBox<EmployeeType> cbEmployeeType = new JComboBox<>();
	private JComboBox<PaymentMethod> cbPaymentMethod = new JComboBox<>();

	private SalariedEmployeeFieldsPanel salariedEmployeeFieldsPanel = new SalariedEmployeeFieldsPanel();
	private HourlyEmployeeFieldsPanel hourlyEmployeeFieldsPanel = new HourlyEmployeeFieldsPanel();
	private CommissionedEmployeeFieldsPanel commissionedEmployeeFieldsPanel = new CommissionedEmployeeFieldsPanel();
	private EmployeeFieldsPanel<? extends EmployeeViewModel> currentEmployeeFieldsPanel;
	
	private enum EmployeeType {
		SALARIED,
		HOURLY,
		COMMISSIONED
	}
	private enum PaymentMethod {
		PAYMASTER,
		DIRECT_DEPOSIT
	}
	private final Map<EmployeeType, EmployeeFieldsPanel<?>> employeeFieldsPanelByEmployeeType = new HashMap<EmployeeType, EmployeeFieldsPanel<?>>() {{
		put(EmployeeType.SALARIED, salariedEmployeeFieldsPanel);
		put(EmployeeType.HOURLY, hourlyEmployeeFieldsPanel);
		put(EmployeeType.COMMISSIONED, commissionedEmployeeFieldsPanel);
	}};
	private final Map<EmployeeType, FieldsPanel> paymentMethodFieldsPanelByPaymentMethod = new HashMap<EmployeeType, FieldsPanel>() {{
	}};

	public AddEmployeeDialog() {
		this(null);
	}
	public AddEmployeeDialog(JFrame parentFrame) {
		super(parentFrame, "Add Employee");
		initUI();
		initCommonFields();
		populateComboBoxes();
		centerParent();
		initListeners();
		initDefaults();
	}
	
	private void initDefaults() {
		cbEmployeeType.setSelectedIndex(0);
		cbPaymentMethod.setSelectedIndex(0);
	}
	
	private void initListeners() {
		cbEmployeeType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentEmployeeFieldsPanel = employeeFieldsPanelByEmployeeType.get((EmployeeType) cbEmployeeType.getSelectedItem());
				updateTypeSpecificPanelsVisibility();
			}
		});
	}
	
	private void updateTypeSpecificPanelsVisibility() {
		employeeFieldsPanelByEmployeeType.values().stream()
			.forEach((it) -> 
				it.setVisible(it == currentEmployeeFieldsPanel)
			);
	}
	
	private void populateComboBoxes() {
		cbEmployeeType.setModel(new DefaultComboBoxModel<>(EmployeeType.values()));
		cbPaymentMethod.setModel(new DefaultComboBoxModel<>(PaymentMethod.values()));
	}

	private void initCommonFields() {
		fieldsPanel.addField("Id", ifEmployeeId);
		fieldsPanel.addField("Name", tfName);
		fieldsPanel.addField("Address", tfAddress);
		fieldsPanel.addField("Type", cbEmployeeType);
	}

	@Override
	public EmployeeViewModel getModel() {
		return fillBase(currentEmployeeFieldsPanel.getModel());
	}
	private EmployeeViewModel fillBase(EmployeeViewModel viewModel) {
		viewModel.employeeId = ifEmployeeId.getInteger();
		viewModel.name = tfName.getText();
		viewModel.address = tfAddress.getText();
		return viewModel;
	}

	private void initUI() {
		setSize(450, 450);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			
			getContentPane().add(panel, BorderLayout.NORTH);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			JPanel panel_1 = new JPanel();
			panel.add(panel_1);
			
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(fieldsPanel, BorderLayout.NORTH);
			
			{
				JPanel typeSpecificFieldsPanels = new JPanel();
				panel_1.add(typeSpecificFieldsPanels, BorderLayout.SOUTH);
				typeSpecificFieldsPanels.setLayout(new BoxLayout(typeSpecificFieldsPanels, BoxLayout.Y_AXIS));
				employeeFieldsPanelByEmployeeType.values().stream()
					.forEach((it) -> typeSpecificFieldsPanels.add(it));
			}
			{
				JPanel panel_2 = new JPanel();
				panel.add(panel_2);
				panel_2.setLayout(new BorderLayout(0, 0));
				
				FieldsPanel fieldsPanel_1 = new FieldsPanel();
				panel_2.add(fieldsPanel_1, BorderLayout.NORTH);
				fieldsPanel_1.addField("Payment Method", cbPaymentMethod);
				{
					JPanel paymentMethodFieldsPanels = new JPanel();
					panel_2.add(paymentMethodFieldsPanels, BorderLayout.SOUTH);
					paymentMethodFieldsPanels.setLayout(new BoxLayout(paymentMethodFieldsPanels, BoxLayout.Y_AXIS));
					paymentMethodFieldsPanelByPaymentMethod.values().stream()
						.forEach((it) -> paymentMethodFieldsPanels.add(it));
				}
			}
			{
				errorMessageLabel = new ValidationErrorMessagesLabel();
				errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				panel.add(errorMessageLabel);
			}
		}
		{
			OkCancelButtonBar okCancelButtonBar = new OkCancelButtonBar(new OkCancelButtonBarListener() {
				@Override
				public void onOk() {
					getListener().onAddEmployee();
				}
				
				@Override
				public void onCancel() {
					getListener().onCancel();
				}
			}, "ADD", "CANCEL");
			getContentPane().add(okCancelButtonBar, BorderLayout.SOUTH);
		}
		
	}

	@Override
	public void setModel(ValidationErrorMessagesModel viewModel) {
		errorMessageLabel.setMessages(viewModel.validationErrorMessages);
	}
}
