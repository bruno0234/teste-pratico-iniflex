import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		String separator = "==================================================";

		System.out.println(separator);
		System.out.println("Creating list of employees...");
		List<Employee> employees = getEmployeesList();

		System.out.println(separator);
		System.out.println("Removing employee named 'João'...");
		employees.removeIf(employee -> employee.getName().equals("João"));

		System.out.println(separator);
		System.out.println(
			"Printing all employees with their data:" + System.lineSeparator()
		);
		employees.forEach(
			employee -> System.out.println(getFormattedEmployeeData(employee))
		);

		System.out.println(separator);
		System.out.println("Increasing employees' salary by 10%...");
		increaseSalaryByPercentage(employees, new BigDecimal("0.1"));

		System.out.println(separator);
		System.out.println(
			"Printing employees by role:" + System.lineSeparator()
		);
		Map<String, List<Employee>> roleToEmployeesMap =
			getRoleToEmployeesMap(employees);
		roleToEmployeesMap.forEach(
			(roleKey, employeesValue) -> {
				System.out.println("------------------------------------");
				System.out.println(roleKey + ":" + System.lineSeparator());
				employeesValue.forEach(
					employee -> System.out.println(employee.getName())
				);
			}
		);

		System.out.println(separator);
		System.out.println(
			"Printing employees whose birthdays are in the 10th or 12th month:"
			+ System.lineSeparator()
		);
		List<Employee> employeesBirthday10thOr12thMonth =
			getEmployeesByBirthMonths(
				employees,
				Set.of(Month.OCTOBER, Month.DECEMBER)
			);
		for (Employee employee : employeesBirthday10thOr12thMonth) {
			System.out.println(employee.getName());
		}

		System.out.println(separator);
		System.out.println(
			"Printing the oldest employees' name and age:"
			+ System.lineSeparator()
		);
		List<Employee> oldestEmployees = getOldestEmployees(employees);
		for (Employee employee : oldestEmployees) {
			int age = Period.between(
				employee.getBirthDate(), LocalDate.now()
			).getYears();
			System.out.printf(
				"Name=%-10sAge=%d years%n", employee.getName(), age
			);
		}

		System.out.println(separator);
		System.out.println(
			"Printing the list of employees in alphabetical order:"
			+ System.lineSeparator()
		);
		employees.sort(Comparator.comparing(Employee::getName));
		employees.forEach(e -> System.out.println(e.getName()));

		System.out.println(separator);
		System.out.println(
			"Printing the total salary of employees:"
				+ System.lineSeparator()
		);
		BigDecimal totalSalary = getTotalSalary(employees);
		System.out.println(getFormattedSalary(totalSalary));

		System.out.println(separator);
		System.out.println(
			"Printing how many minimum wages each employee earns:"
				+ System.lineSeparator()
		);
		BigDecimal minimumWage = new BigDecimal("1212.00");
		for (Employee employee : employees) {
			BigDecimal totalMinimumWages =
				employee.getSalary().divide(minimumWage, RoundingMode.HALF_DOWN);
			System.out.printf(
				"Name=%-10sTotal number of minimum wages=%f%n",
				employee.getName(),	totalMinimumWages
			);
		}
	}

	private static List<Employee> getEmployeesList() {
		List<Employee> employees = new ArrayList<>();
		employees.add(
			new Employee(
				"Maria",
				LocalDate.of(2000, Month.OCTOBER, 18),
				new BigDecimal("2009.44"),
				"Operador"
			)
		);
		employees.add(
			new Employee(
				"João",
				LocalDate.of(1990, Month.MAY, 12),
				new BigDecimal("2284.38"),
				"Operador"
			)
		);
		employees.add(
			new Employee(
				"Caio",
				LocalDate.of(1961, Month.MAY, 2),
				new BigDecimal("9836.14"),
				"Coordenador"
			)
		);
		employees.add(
			new Employee(
				"Miguel",
				LocalDate.of(1988, Month.OCTOBER, 14),
				new BigDecimal("19119.88"),
				"Diretor"
			)
		);
		employees.add(
			new Employee(
				"Alice",
				LocalDate.of(1995, Month.JANUARY, 5),
				new BigDecimal("2234.68"),
				"Recepcionista"
			)
		);
		employees.add(
			new Employee(
				"Heitor",
				LocalDate.of(1999, Month.NOVEMBER, 19),
				new BigDecimal("1582.72"),
				"Operador"
			)
		);
		employees.add(
			new Employee(
				"Arthur",
				LocalDate.of(1993, Month.MARCH, 31),
				new BigDecimal("4071.84"),
				"Contador"
			)
		);
		employees.add(
			new Employee(
				"Laura",
				LocalDate.of(1994, Month.JULY, 8),
				new BigDecimal("3017.45"),
				"Gerente"
			)
		);
		employees.add(
			new Employee(
				"Heloísa",
				LocalDate.of(2003, Month.MAY, 24),
				new BigDecimal("1606.85"),
				"Eletricista"
			)
		);
		employees.add(
			new Employee(
				"Helena",
				LocalDate.of(1996, Month.SEPTEMBER, 2),
				new BigDecimal("2799.93"),
				"Gerente"
			)
		);
		return employees;
	}

	private static String getFormattedSalary(BigDecimal salary) {
		if (salary == null) {
			throw new IllegalArgumentException(
				"salary parameter must not be null"
			);
		}
		NumberFormat ptBrNumberFormat = NumberFormat.getInstance(
			Locale.of("pt", "BR")
		);
		String formattedSalary = ptBrNumberFormat.format(salary);
		return formattedSalary;
	}

	private static String getFormattedBirthDate(LocalDate birthDate) {
		if (birthDate == null) {
			throw new IllegalArgumentException(
				"birthDate parameter must not be null"
			);
		}
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern(
			"dd/MM/yyyy"
		);
		String formattedBirthDate = birthDate.format(datePattern);
		return formattedBirthDate;
	}

	private static String getFormattedEmployeeData(Employee employee) {
		if (employee == null) {
			throw new IllegalArgumentException(
				"employee parameter must not be null"
			);
		}
		String formattedSalary = getFormattedSalary(employee.getSalary());
		String formattedBirthDate =
			getFormattedBirthDate(employee.getBirthDate());
		String formattedEmployeeData = String.format(
			"Name=%-10sBirth Date=%-15sSalary=%-15sRole=%s",
			employee.getName(),
			formattedBirthDate,
			formattedSalary,
			employee.getRole()
		);
		return formattedEmployeeData;
	}

	private static void increaseSalaryByPercentage(
		List<Employee> employees, BigDecimal percentageIncrease
	) {
		if (employees == null) {
			throw new IllegalArgumentException(
				"employees parameter must not be null"
			);
		}
		if (percentageIncrease == null) {
			throw new IllegalArgumentException(
				"percentageIncrease parameter must not be null"
			);
		}
		BigDecimal factor = new BigDecimal(1).add(percentageIncrease);
		employees.forEach(f -> f.setSalary(f.getSalary().multiply(factor)));
	}

	private static Map<String, List<Employee>> getRoleToEmployeesMap(
		List<Employee> employees
	) {
		if (employees == null) {
			throw new IllegalArgumentException(
				"employees parameter must not be null"
			);
		}
		Map<String, List<Employee>> roleToEmployeesHashMap = new HashMap<>();
		employees.forEach(
			e -> {
				if (!roleToEmployeesHashMap.containsKey(e.getRole())) {
					roleToEmployeesHashMap.put(
						e.getRole(), new ArrayList<>()
					);
				}
				roleToEmployeesHashMap.get(e.getRole()).add(e);
			}
		);
		return roleToEmployeesHashMap;
	}

	private static List<Employee> getEmployeesByBirthMonths(
		List<Employee> employees, Set<Month> birthMonths
	) {
		if (employees == null) {
			throw new IllegalArgumentException(
				"employees parameter must not be null"
			);
		}
		if (birthMonths == null) {
			throw new IllegalArgumentException(
				"birthMonths parameter must not be null"
			);
		}
		List<Employee> employeesByBirthMonths = new ArrayList<>();
		for (Employee employee : employees) {
			if (birthMonths.contains(employee.getBirthDate().getMonth())) {
				employeesByBirthMonths.add(employee);
			}
		}
		return employeesByBirthMonths;
	}

	private static List<Employee> getOldestEmployees(
		List<Employee> employees
	) {
		if (employees == null) {
			throw new IllegalArgumentException(
				"employees parameter must not be null"
			);
		}
		/*
			The result is a list because we might have two or more
			employees with the same birthdate
		*/
		List<Employee> oldestEmployees = new ArrayList<>();
		for (Employee employee : employees) {
			if (oldestEmployees.isEmpty()) {
				oldestEmployees.add(employee);
			} else {
				LocalDate oldestEmployeeBirthDate =
					oldestEmployees.getFirst().getBirthDate();
				LocalDate employeeBirthDate = employee.getBirthDate();
				if (employeeBirthDate.isBefore(oldestEmployeeBirthDate)) {
					oldestEmployees.clear();
					oldestEmployees.add(employee);
				} else if (employeeBirthDate.isEqual(oldestEmployeeBirthDate)) {
					oldestEmployees.add(employee);
				}
			}
		}
		return oldestEmployees;
	}

	private static BigDecimal getTotalSalary(List<Employee> employees) {
		if (employees == null) {
			throw new IllegalArgumentException(
				"employees parameter must not be null"
			);
		}
		BigDecimal totalSalary = new BigDecimal(0);
		for (Employee employee : employees) {
			totalSalary = totalSalary.add(employee.getSalary());
		}
		return totalSalary;
	}
}
