package oracle.ted.ipspregled.bean;


import oracle.ted.ipspregled.common.ADFUtil;
import oracle.ted.ipspregled.common.CommonActions;
import oracle.ted.ipspregled.common.JSFUtils;


import javax.faces.event.ActionEvent;

/*import oracle.adfinternal.view.faces.bi.util.JsfUtils;*/

public class CommitDeleteBean {
    public CommitDeleteBean() {
    }
    /**
         * Deletes the current selected row in the Shopping Cart.
         * 
         * @param actionEvent 
         */
     
    public void onDeleteItem(ActionEvent actionEvent) {
            ADFUtil.invokeEL("#{bindings.Delete.execute}");
            ADFUtil.invokeEL("#{bindings.Commit.execute}");
            System.out.println("Deleted");
        }
    public void onDeleteItem1(ActionEvent actionEvent) {
            ADFUtil.invokeEL("#{bindings.Delete1.execute}");
            /*ADFUtil.invokeEL("#{bindings.Commit1.execute}");*/
           /* System.out.println("Deleted1");*/
        }
    
    
    
}
