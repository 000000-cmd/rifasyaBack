package org.rifasya.main.dto.request;

public class ThirdPartyWithUserRequestDTO {

    private UserRequestDTO user;
    private ThirdPartyRequestDTO thirdParty;

    public UserRequestDTO getUser() {
        return user;
    }

    public void setUser(UserRequestDTO user) {
        this.user = user;
    }

    public ThirdPartyRequestDTO getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdPartyRequestDTO thirdParty) {
        this.thirdParty = thirdParty;
    }
}

