package hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.core.entity.paymentschedule;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import hu.daniel.hari.exercises.cleanarchitecture.payrollcasestudy.core.entity.DateInterval;

import java.time.LocalDate;

import org.junit.Test;

public class MontlhyPaymentScheduleTest {

	MontlhyPaymentSchedule montlhyPaymentSchedule = new MontlhyPaymentSchedule();

	@Test
	public void isPaydayOnLastDayOfMonth_ShouldBeTrue() throws Exception {
		assertThat(montlhyPaymentSchedule.isPayday(LocalDate.of(2015, 12, 31)), is(true));
		assertThat(montlhyPaymentSchedule.isPayday(LocalDate.of(2015, 11, 30)), is(true));
	}
	@Test
	public void isPaydayOnNotLastDayOfMonth_ShouldBeFalse() throws Exception {
		assertThat(montlhyPaymentSchedule.isPayday(LocalDate.of(2015, 11, 24)), is(false));
		assertThat(montlhyPaymentSchedule.isPayday(LocalDate.of(2015, 12, 01)), is(false));
		assertThat(montlhyPaymentSchedule.isPayday(LocalDate.of(2015, 12, 02)), is(false));
	}
	
	@Test
	public void GetIntervalOnPayday() {
		DateInterval dateInterval = montlhyPaymentSchedule.getPayIntervalOfPayday(LocalDate.of(2015, 12, 31));
		assertThat(dateInterval.from, 	is(LocalDate.of(2015, 12, 01)));
		assertThat(dateInterval.to, 	is(LocalDate.of(2015, 12, 31)));
	}

	@Test(expected=RuntimeException.class)
	public void GetIntervalOnNonPayday_ShouldThrowException() {
		montlhyPaymentSchedule.getPayIntervalOfPayday(LocalDate.of(2015, 11, 1));
	}
	
}