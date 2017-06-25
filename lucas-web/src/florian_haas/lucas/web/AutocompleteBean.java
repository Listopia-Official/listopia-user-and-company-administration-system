package florian_haas.lucas.web;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;

import florian_haas.lucas.business.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.web.util.WebUtils;

@Named
@RequestScoped
public class AutocompleteBean {

	@EJB
	private AccountBeanLocal accountBean;

	@EJB
	private EntityBeanLocal entityBean;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private CompanyBeanLocal companyBean;

	@EJB
	private RoomBeanLocal roomBean;

	@EJB
	private JobBeanLocal jobBean;

	@EJB
	private LoginBeanLocal loginBean;

	@EJB
	private EmploymentBeanLocal employmentBean;

	@EJB
	private ItemBeanLocal itemBean;

	private Integer resultsSize = 11;

	public List<? extends ReadOnlyAccount> completeAccount(String data) {
		return accountBean.getAccountsByData(data, resultsSize);
	}

	public List<? extends ReadOnlyRoomSection> completeRoomSection(String data) {
		return roomBean.getRoomSectionsByData(data, resultsSize);
	}

	public List<? extends ReadOnlyUser> completeUser(String data) {
		return userBean.getUsersByData(data, resultsSize);
	}

	public List<? extends ReadOnlyCompany> completeCompany(String data) {
		return companyBean.getCompaniesByData(data, resultsSize);
	}

	public List<? extends ReadOnlyJob> completeJob(String data) {
		return jobBean.getJobsByData(data, resultsSize);
	}

	public List<? extends ReadOnlyLoginUser> completeLoginUser(String data) {
		return loginBean.getLoginUsersByData(data, resultsSize);
	}

	public List<? extends ReadOnlyAccountOwner> completeAccountOwner(String data) {
		return accountBean.getAccountOwnersByData(data, resultsSize);
	}

	public List<? extends ReadOnlyEmployment> completeEmployment(String data) {
		return employmentBean.getEmploymentsByData(data, resultsSize);
	}

	public List<? extends ReadOnlyItem> completeItem(String data) {
		return itemBean.getItemsByData(data, resultsSize);
	}

	private Long getIdParam() {
		Long id = null;
		String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("entityId");
		if (param != null) {
			try {
				id = Long.parseLong(param);
			}
			catch (NumberFormatException e) {}
		}
		return id;
	}

	private StreamedContent getDefaultUserImage() {
		return WebUtils
				.getSVGImage(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/resources/images/user_male.svg"));
	}

	public StreamedContent getImageOfAccountOwner() {
		StreamedContent image = getDefaultUserImage();
		Long id = getIdParam();
		if (id != null) {
			image = getImageOfUser(image, id);
			if (WebUtils.isPermitted(EnumPermission.COMPANY_GET_COMPANY_TYPE_FROM_ID) && entityBean.exists(id, ReadOnlyCompany.class)) {
				EnumCompanyType type = companyBean.getCompanyTypeFromId(id);
				switch (type) {
					case CIVIL:
						image = WebUtils.getSVGImage(
								FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/resources/images/factory.svg"));
						break;
					case STATE:
						image = WebUtils.getSVGImage(FacesContext.getCurrentInstance().getExternalContext()
								.getResourceAsStream("/WEB-INF/resources/images/government.svg"));
						break;
				}
			}
		}
		return image;
	}

	private StreamedContent getImageOfUser(StreamedContent defaultImage, Long userId) {
		StreamedContent image = defaultImage;
		if (userId != null && WebUtils.isPermitted(EnumPermission.USER_GET_IMAGE_FROM_ID) && entityBean.exists(userId, ReadOnlyUser.class)) {
			byte[] userImage = userBean.getImage(userId);
			if (userImage != null) {
				image = WebUtils.getJPEGImage(userImage);
			}
		}
		return image;
	}

	public StreamedContent getImageOfLoginUser() {
		Long loginUserId = getIdParam();
		Long userId = null;
		if (loginUserId != null && entityBean.exists(loginUserId, ReadOnlyLoginUser.class) && loginBean.isBoundLoginUser(loginUserId)) {
			ReadOnlyLoginUser user = loginBean.findLoginUserById(loginUserId);
			userId = user.getUser() != null ? user.getUser().getId() : null;
		}
		return getImageOfUser(getDefaultUserImage(), userId);
	}

	public Integer getResultsSize() {
		return resultsSize;
	}

}
