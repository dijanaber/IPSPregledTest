package oracle.ted.ipspregled.common;


import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class CommonActions {
    public CommonActions() {
        super();
    }
    public void commit(ActionEvent actionEvent) {
    if (ADFUtil.hasChanges()) {
    // allow derived beans to handle before commit actions
    onBeforeCommit(actionEvent);
    // allow derived beans to handle commit actions
    onCommit(actionEvent);
    // allow derived beans to handle after commit actions
    onAfterCommit(actionEvent);
    } else {
    // display "No changes to commit" message
    JSFUtils.addFacesInformationMessage("No changes to commit");
    }
    }
    protected void onBeforeCommit(ActionEvent actionEvent) {
    }
   
    protected void onCommit(ActionEvent actionEvent) {
    // execute commit
        ADFUtil.invokeEL("#{bindings.Commit.execute}");
    }
    protected void onAfterCommit(ActionEvent actionEvent) {
    // display "Changes were committed successfully" message
    JSFUtils.addFacesInformationMessage("Changes were committed successfully");
    }
    
    /**************** kreiranje novog sloga  ****************/
    public void create(final ActionEvent actionEvent) {
    if (ADFUtil.hasChanges()) {
    onCreatePendingChanges(actionEvent);
    } else {
    onContinueCreate(actionEvent);
    }
    }
    protected void onBeforeCreate(final ActionEvent actionEvent) {
    // commit before creating a new record
       /* ADFUtil.execOperation(Operations.COMMIT);*/
        ADFUtil.invokeEL("#{bindings.Commit.execute}");
    }
    public void onCreate(final ActionEvent actionEvent) {
        ADFUtil.invokeEL("#{bindings.CreateInsert.execute}");
    }
    protected void onAfterCreate(ActionEvent actionEvent) {
    }
    public void onCreatePendingChanges(final ActionEvent actionEvent) {
    ADFUtil.showPopup("CreatePendingChanges");
    }
    public void onContinueCreate(final ActionEvent actionEvent) {
        CommonActions actions = getCommonActions();
        actions.onBeforeCreate(actionEvent);
        actions.onCreate(actionEvent);
        actions.onAfterCreate(actionEvent);
    }
    
    /*********************** brisanje sloga *******************/
     public void delete(final ActionEvent actionEvent) {
     onConfirmDelete(actionEvent);
     }
    protected void onBeforeDelete(ActionEvent actionEvent) {
    // commit before creating a new record
        /*ADFUtil.invokeEL("#{bindings.Commit.execute}");*/
    }
    protected void onBeforeDelete1(ActionEvent actionEvent) {
    // commit before creating a new record
        ADFUtil.invokeEL("#{bindings.Commit1.execute}");
    }
    public void onDelete(ActionEvent actionEvent) {
        ADFUtil.invokeEL("#{bindings.Delete.execute}");
    }
    public void onDelete1(ActionEvent actionEvent) {
        ADFUtil.invokeEL("#{bindings.Delete1.execute}");
    }
    protected void onAfterDelete(ActionEvent actionEvent) {
        ADFUtil.invokeEL("#{bindings.Commit.execute}");
    }
    protected void onAfterDelete1(ActionEvent actionEvent) {
    }
    public void onDeletePendingChanges(ActionEvent actionEvent) {
    ADFUtil.showPopup("CreatePendingChanges");
    }
    private CommonActions getCommonActions() {
    CommonActions actions =
    (CommonActions)JSFUtils.resolveExpression("#{"
    + getManagedBeanName() + "}");
    if (actions == null) {
    actions = this;
    }
    return actions;
    }
    public void onContinueDelete(final ActionEvent actionEvent) {
    CommonActions actions = getCommonActions();
    actions.onBeforeDelete(actionEvent);
    actions.onDelete(actionEvent);
    actions.onAfterDelete(actionEvent);
    /*BindingContainer bindings = getBindings();
    
    OperationBinding operationBinding = bindings.getOperationBinding("Delete");
    Object result = operationBinding.execute();
    if (!operationBinding.getErrors().isEmpty()) {
        //handle errors if any
        //...
        return;
    }*/      
    }
    public void onContinueDelete1(final ActionEvent actionEvent) {
    CommonActions actions = getCommonActions();
    actions.onBeforeDelete1(actionEvent);
    actions.onDelete1(actionEvent);
   /*ADFUtil.invokeEL("#{bindings.Delete1.execute}");*/
    actions.onAfterDelete1(actionEvent);
    }
    public void onConfirmDelete(final ActionEvent actionEvent) {
    ADFUtil.showPopup("DeleteConfirmation");
    }
    private String getManagedBeanName() {
    return getPageId().replace("/", "").replace(".jspx", "");
    }
    public String getPageId() {
    FacesContext fctx = FacesContext.getCurrentInstance();
    return fctx.getViewRoot().getViewId().substring(
    fctx.getViewRoot().getViewId().lastIndexOf("/"));
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void deleteDialogListener(DialogEvent dialogEvent) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete");
            operationBinding.execute();
            
            if (operationBinding.getErrors().isEmpty()) {
                operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();      
                }
    }
}

