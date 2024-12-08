package hotil.baemo.domains.clubs.adapter.input.rest.club;

import hotil.baemo.domains.clubs.adapter.input.rest.club.dto.request.CommandClubsRequest;
import hotil.baemo.domains.clubs.adapter.output.persist.club.entity.ClubsEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.club.repository.ClubsJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.member.repository.ClubsMemberJpaRepository;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommandClubsApiTest extends ControllerTestBaseSupport {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubsJpaRepository clubsJpaRepository;
    @Autowired
    private ClubsMemberJpaRepository clubsMemberJpaRepository;
    private MockMultipartFile clubsProfileImage;
    private MockMultipartFile clubsBackgroundImage;

    @BeforeEach
    void setImportUserServiceSupport() {
        userSupport.setUpUser();
        clubsProfileImage = new MockMultipartFile("clubsProfileImage", "clubsProfileImage.png", MediaType.IMAGE_PNG_VALUE, "clubsProfileImage".getBytes());
        clubsBackgroundImage = new MockMultipartFile("clubsBackgroundImage", "clubsBackgroundImage.png", MediaType.IMAGE_PNG_VALUE, "clubsBackgroundImage".getBytes());
    }

    @Test
    void 클럽_생성_요청에_성공할_것이다() throws Exception {
        final var createDTO = new MockMultipartFile("createClubsDTO", "createClubsDTO", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(monkey.giveMeOne(CommandClubsRequest.CreateClubsDTO.class)));

        final var body = mockMvc.perform(multipart("/api/clubs")
                .file(clubsProfileImage)
                .file(clubsBackgroundImage)
                .file(createDTO)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").isNotEmpty())
            .andExpect(jsonPath("$.payload.clubsId").isNotEmpty())
            .andReturn()
            .getResponse()
            .getContentAsString();
        final var clubsId = objectMapper.readTree(body).path("payload").path("clubsId").asLong();
        Assertions.assertThat(clubsJpaRepository.existsById(clubsId)).isTrue();
    }

    @Test
    void 클럽_삭제_요청에_성공할_것이다() throws Exception {
        final var createDTO = new MockMultipartFile("createClubsDTO", "createClubsDTO", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(monkey.giveMeOne(CommandClubsRequest.CreateClubsDTO.class)));

        final var body = mockMvc.perform(multipart("/api/clubs")
                .file(clubsProfileImage)
                .file(clubsBackgroundImage)
                .file(createDTO))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").isNotEmpty())
            .andExpect(jsonPath("$.payload.clubsId").isNotEmpty())
            .andReturn()
            .getResponse()
            .getContentAsString();
        final var clubsId = objectMapper.readTree(body).path("payload").path("clubsId").asLong();

        mockMvc.perform(delete("/api/clubs/{clubsId}", clubsId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        final var clubsEntity = clubsJpaRepository.findById(clubsId);
        Assertions.assertThat(clubsEntity).isNotPresent();

    }

    @Test
    void 클럽_수정_요청에_성공할_것이다() throws Exception {
        final var createDTO = new MockMultipartFile("createClubsDTO", "createClubsDTO", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(monkey.giveMeOne(CommandClubsRequest.CreateClubsDTO.class)));

        final var body = mockMvc.perform(multipart("/api/clubs")
                .file(clubsProfileImage)
                .file(clubsBackgroundImage)
                .file(createDTO))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").isNotEmpty())
            .andExpect(jsonPath("$.payload.clubsId").isNotEmpty())
            .andReturn()
            .getResponse()
            .getContentAsString();
        final var clubsId = objectMapper.readTree(body).path("payload").path("clubsId").asLong();

        //
        final var updateRequest = monkey.giveMeBuilder(CommandClubsRequest.UpdateClubsDTO.class).set("clubsId", clubsId).sample();
        final var updateDTO = new MockMultipartFile("updateClubsDTO", "updateClubsDTO", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(updateRequest));
        final var updateClubsProfileImage = new MockMultipartFile("clubProfileUrl", "clubProfileUrl.png", MediaType.IMAGE_PNG_VALUE, "clubProfileUrl".getBytes());
        final var updateClubsBackgroundImage = new MockMultipartFile("clubsBackgroundImage", "clubsBackgroundImage.png", MediaType.IMAGE_PNG_VALUE, "clubsBackgroundImage".getBytes());


        mockMvc.perform(multipart("/api/clubs")
                .file(updateDTO)
//                .file(updateClubsProfileImage)
//                .file(updateClubsBackgroundImage)
                .with(req -> {
                    req.setMethod(HttpMethod.PUT.name());
                    return req;
                })
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").isNotEmpty())
            .andExpect(jsonPath("$.payload").isEmpty());

        final ClubsEntity updated = clubsJpaRepository.findById(clubsId).orElseThrow();
        assertAll(() -> {
            Assertions.assertThat(updated.getClubsName()).isEqualTo(updateRequest.clubsName());
            Assertions.assertThat(updated.getClubsLocation()).isEqualTo(updateRequest.clubsLocation());
            Assertions.assertThat(updated.getClubsSimpleDescription()).isEqualTo(updateRequest.clubsSimpleDescription());
            Assertions.assertThat(updated.getClubsDescription()).isEqualTo(updateRequest.clubsDescription());
        });
    }
}
