public class Payroll {
    public static final int INITIAL_MAXIMUM_SIZE = 1024;

    public Payroll() {
	/* your code */
        people = new Employee[INITIAL_MAXIMUM_SIZE];
        maximum_size = INITIAL_MAXIMUM_SIZE;
        current_size = 0;
    }
    
    public void add_employee(Employee newbie) {
	/* your code */
        if (current_size == maximum_size) {
            double_size();
        }

        people[current_size] = newbie;
        current_size++;
    }

    public void remove_employee(int i) throws EmployeeIndexException {
       if (i < 0 || i >= current_size) {
        throw new EmployeeIndexException("Not a valid employee index: " + i);
       }
       // Replace with last employee
       people[i] = people[current_size - 1];

       // Clear last slot
       people[current_size - 1] = null;

       current_size--;
    }
    
    public int find_employee(String name) throws EmployeeNotFoundException {
	/* your code */
        for (int i = 0; i < current_size; i++) {
            if (name.equals(people[i].name))
                return i;
        }
        throw new EmployeeNotFoundException("Employee not found: " + name);
    }

    public void add_payroll(Payroll source) {
	/* your code */
        for (int i = 0; i < source.current_size; i++) {
            Employee person = source.people[i];
            this.add_employee(person);
        }
    }

    public void copy_payroll(Payroll source) {
        this.maximum_size = source.maximum_size;
        this.people = new Employee[this.maximum_size];

        this.current_size = source.current_size;
        for (int i = 0; i < this.current_size; i++) {
            this.people[i] = source.people[i];
        }
    }

    public int size() {
        return current_size;
    }

    public void print() {
        System.out.print("[");
        for (int i = 0; i < current_size; i++) {
            System.out.print(people[i]);
            if (i < current_size - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    

    /* only methods inside Payroll can access these variables */
    private Employee people[];
    private int maximum_size;
    private int current_size;

    private void double_size() {
        Employee[] temp = new Employee[maximum_size * 2];

        for (int i = 0; i < current_size; i++) {
            temp[i] = people[i];
        }

        people = temp;
        maximum_size = temp.length;
    }
}
