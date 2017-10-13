package models.forms;

import java.time.LocalDate;

/**
 * Project Name: MemoView
 */
public class MockedForm extends GenericForm {

    private static int staticInt = 0;

    public MockedForm(String recipient){
        setDateIssued(LocalDate.now());
        setSubject(":DDD");
        setRecipient(recipient);
        setTypeOfForm(String.valueOf(staticInt));
        staticInt++;
    }


    public void editForm(){
        staticInt = 0;
    }
}
