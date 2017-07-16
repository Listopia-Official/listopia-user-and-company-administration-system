package florian_haas.lucas.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.ReadOnlyIdCard;
import florian_haas.lucas.validation.ValidEntityId;
import florian_haas.lucas.web.converter.AccountOwnerConverter;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class AttendanceScanBean implements Serializable {

	private static final long serialVersionUID = -5571376314067403822L;

	@EJB
	private AttendanceBeanLocal attendanceBean;

	@EJB
	private IdCardBeanLocal idCardBean;

	@ValidEntityId(entityClass = ReadOnlyIdCard.class, message = "{lucas.application.attendanceScanScreen.scan.message.invalidCardId}")
	private Long idCard = 0l;

	public Long getIdCard() {
		return this.idCard;
	}

	public void setIdCard(Long idCard) {
		this.idCard = idCard;
	}

	public void scan() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(idCardBean.findIdCardById(idCard).getOwner(), AccountOwnerConverter.CONVERTER_ID));
			Boolean entered = attendanceBean.scan(idCard);
			params.add(WebUtils.getTranslatedMessage("lucas.application.attendanceScanScreen.scan.message.success" + (entered ? "1" : "2")));
			return true;
		}, "lucas.application.attendanceScanScreen.scan.message", (exception, params) -> {
			String ret = null;
			switch (exception.getMark()) {
				case AttendanceBeanLocal.USER_CARD_BLOCKED_EXCEPTION_MARKER:
					ret = "lucas.application.attendanceScanScreen.scan.message.idCardBlocked";
					break;
				case AttendanceBeanLocal.USER_CARD_INVALID_DATE_EXCEPTION_MARKER:
					ret = "lucas.application.attendanceScanScreen.scan.message.idCardInvalidDate";
					break;
			}
			return ret == null ? null : WebUtils.getTranslatedMessage(ret, idCard, params.get(0));
		});
		idCard = null;
	}

}
