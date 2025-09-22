package org.rifasya.main.dto.response;

import org.rifasya.main.dto.request.ThirdPartyRequestDTO;
import org.rifasya.main.dto.request.UserRequestDTO;

public class ThirdPartyWhitUserResponseDTO {

    private UserResponseDTO user;
    private ThirdPartyResponseDTO thirdParty;

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public ThirdPartyResponseDTO getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdPartyResponseDTO thirdParty) {
        this.thirdParty = thirdParty;
    }
}
