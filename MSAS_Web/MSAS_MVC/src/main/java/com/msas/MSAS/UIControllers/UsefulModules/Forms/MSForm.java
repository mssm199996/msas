package com.msas.MSAS.UIControllers.UsefulModules.Forms;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import com.msas.MSAS.DomainModel.Authentification.Compte;
import com.msas.MSAS.UIControllers.UsefulModules.Button.MSButton;
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public abstract class MSForm<T> extends Dialog {

	private static final long serialVersionUID = 6238088969942017393L;

	protected FormLayout formLayout;
	protected Consumer<T> onInsertSuccessfullCallback;
	protected Binder<T> binder;
	protected T bindedEntity;
	protected boolean update;

	@PostConstruct
	private void onFormAllocatedCallback() {
		this.constructHeader();
		this.constructForm();
		this.constructFooter();

		this.addOpenedChangeListener(event -> {
			this.binder.setBean(this.getBindedEntity());
			this.binder.readBean(this.binder.getBean());

			this.onOpenCallback();

			if (this.getOnShowFocusableComponent() != null)
				this.getOnShowFocusableComponent().focus();
		});
	}

	protected void constructForm() {
		this.formLayout = new FormLayout();
		this.binder = new Binder<T>(this.getEntityClassForBinder());

		this.constructFormFields();

		Map<String, String> beanFieldsToLabels = this.beanFieldsToLabels();
		Map<String, VaadinIcon> beansFieldsToIcons = this.beanFieldsToIcons();

		Map<String, Component> beanFieldsToDisplayedUIFields = this
				.beanFieldsToDisplayedUIFields();

		Set<String> beanFields = beanFieldsToDisplayedUIFields.keySet();

		for (String fieldName : beanFields) {
			VaadinIcon vaadinIcon = beansFieldsToIcons.get(fieldName);
			Component component = beanFieldsToDisplayedUIFields.get(fieldName);

			Icon icon = vaadinIcon.create();
			icon.setColor("BLUE");

			if (component instanceof HasSize)
				((HasSize) component).setWidthFull();

			HorizontalLayout formItem = new HorizontalLayout();
			formItem.add(icon);
			formItem.add(component);
			formItem.setDefaultVerticalComponentAlignment(Alignment.CENTER);
			formItem.setAlignItems(Alignment.CENTER);

			this.formLayout.add(formItem);
		}

		Map<String, AbstractSinglePropertyField<?, ?>> beanFieldsToUIFields = this
				.beanFieldsToUIFields();

		beanFields = beanFieldsToUIFields.keySet();

		for (String fieldName : beanFields) {
			AbstractSinglePropertyField<?, ?> uiField = beanFieldsToUIFields
					.get(fieldName);

			String placeHolder = beanFieldsToLabels.get(fieldName);
			uiField.getElement().setProperty("label", placeHolder);

			this.binder.bind(uiField, fieldName);
		}

		this.add(this.formLayout);
	}

	private void constructHeader() {
		H2 title = new H2();
		title.setText(this.getFormTitle());
		title.addClassName("mt-0");

		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidthFull();
		layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		layout.setAlignItems(Alignment.CENTER);
		layout.add(title);

		this.add(layout);
	}

	private void constructFooter() {
		MSButton confirm = new MSButton("Confirmer", "success",
				VaadinIcon.CHECK_CIRCLE_O, event -> {
					try {
						T entity = this.binder.getBean();

						this.binder.writeBean(entity);

						this.onBeforeSaveCallback(entity);
						this.getRepository().save(entity);

						if (this.getOnInsertSuccessfullCallback() != null)
							this.getOnInsertSuccessfullCallback()
									.accept(entity);

						if (this.isUpdate())
							this.close();
						else {
							this.binder.setBean(this.instanciateEntity());

							this.getOnShowFocusableComponent().focus();
						}
					} catch (ValidationException e) {
						Notification.show(e.getMessage(), 3000,
								Position.TOP_END);
					}
				});

		confirm.addClickShortcut(Key.ENTER);

		MSButton cancel = new MSButton("Annuler", "error", VaadinIcon.EXIT_O,
				event -> {
					this.close();
				});
		cancel.addClickShortcut(Key.ESCAPE);

		VerticalLayout layout = new VerticalLayout();
		layout.setWidthFull();
		layout.setAlignItems(Alignment.END);

		HorizontalLayout wrapper = new HorizontalLayout();
		wrapper.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		wrapper.setAlignItems(Alignment.CENTER);
		wrapper.add(cancel);
		wrapper.add(confirm);

		layout.add(wrapper);

		this.add(layout);
	}

	public Compte getLoggedInAccount() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof Compte) {
			return (Compte) principal;
		}

		return null;
	}

	public abstract String getFormTitle();

	public abstract Map<String, Component> beanFieldsToDisplayedUIFields();

	public abstract Map<String, AbstractSinglePropertyField<?, ?>> beanFieldsToUIFields();

	public abstract Map<String, VaadinIcon> beanFieldsToIcons();

	public abstract Map<String, String> beanFieldsToLabels();

	public abstract Class<T> getEntityClassForBinder();

	public abstract T instanciateEntity();

	protected abstract Focusable<?> getOnShowFocusableComponent();

	protected abstract void constructFormFields();

	protected abstract JpaRepository<T, ?> getRepository();

	public Consumer<T> getOnInsertSuccessfullCallback() {
		return onInsertSuccessfullCallback;
	}

	public void setOnPersistSuccessfullCallback(
			Consumer<T> onInsertSuccessfullCallback) {
		this.onInsertSuccessfullCallback = onInsertSuccessfullCallback;
	}

	public T getBindedEntity() {
		return bindedEntity;
	}

	public void setBindedEntity(T bindedEntity) {
		this.bindedEntity = bindedEntity;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public void onBeforeSaveCallback(T entity) {

	}
	
	public void onOpenCallback(){
		
	}
}
