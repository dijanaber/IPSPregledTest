package oracle.ted.ipspregled.view;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.adf.model.BindingContext;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.DataControl;
import oracle.binding.ManagedDataControl;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;

public class getValue implements Serializable {
    public getValue() {
    }

    private RichPanelBox pbBinding;

    

    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    private void invokeMethodExpression(String string, Class<Object> object, Class<SelectionEvent> selectionEvent,
                                        SelectionEvent selectionEvent1) {
    }
    
    public void selectionListener(SelectionEvent selectionEvent) {
        // Add event code here...
        //##{bindings.checklistStructureVO1.treeModel.makeCurrent}
        invokeMethodExpression("#{bindings.IpsMntFolder1.treeModel.makeCurrent}", Object.class, SelectionEvent.class, selectionEvent);
        
        RichTree tree = (RichTree)selectionEvent.getSource();
           TreeModel model = (TreeModel)tree.getValue();      
           //get selected nodes
           RowKeySet rowKeySet = selectionEvent.getAddedSet();
        Iterator rksIterator = rowKeySet.iterator();
        //Validating for single select only. Need to check for multiselect
        while (rksIterator.hasNext()) {
            List key = (List)rksIterator.next();
            JUCtrlHierBinding treeBinding = null;
            CollectionModel collectionModel = (CollectionModel)tree.getValue();
            treeBinding = (JUCtrlHierBinding)collectionModel.getWrappedData();            
            JUCtrlHierNodeBinding nodeBinding = null;
            nodeBinding = treeBinding.findNodeByKeyPath(key);
            Row rw = nodeBinding.getRow();
      
            String p_folder_id = (String)rw.getAttribute("FolderId");
           
            Map pageFlowScope = ADFContext.getCurrent().getPageFlowScope();
             pageFlowScope.put("folderId", p_folder_id);
             
            BindingContainer bindings = getBindings();
        
         OperationBinding ob1 = bindings.getOperationBinding("executeSearchVo");
     
           ob1.getParamsMap().put("p_folder_id", p_folder_id);
       
           Object result = ob1.execute();
        
           if (!ob1.getErrors().isEmpty()){
              List errorList = ob1.getErrors();
               System.out.println("ERROR IN VC EXECUTION");
            //g Capture and handle Error
          }   
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPbBinding());
    }
    }

    public void setPbBinding(RichPanelBox pbBinding) {
        this.pbBinding = pbBinding;
    }

    public RichPanelBox getPbBinding() {
        return pbBinding;
    }
}
