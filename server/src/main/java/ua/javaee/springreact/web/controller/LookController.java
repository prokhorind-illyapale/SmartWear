package ua.javaee.springreact.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.javaee.springreact.web.converter.AbstractConverter;
import ua.javaee.springreact.web.data.LookData;
import ua.javaee.springreact.web.data.UserClothAttributeData;
import ua.javaee.springreact.web.data.UserData;
import ua.javaee.springreact.web.entity.Comment;
import ua.javaee.springreact.web.entity.Look;
import ua.javaee.springreact.web.facade.LookFacade;
import ua.javaee.springreact.web.facade.UserClothAttributeFacade;
import ua.javaee.springreact.web.facade.UserFacade;
import ua.javaee.springreact.web.form.lookforms.LookForm;
import ua.javaee.springreact.web.service.CommentService;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static ua.javaee.springreact.web.util.error.ErrorHelper.processingErrors;
import static ua.javaee.springreact.web.util.error.ErrorTypes.*;

/**
 * Created by kleba on 24.02.2019.
 */
@RestController
@RequestMapping(value = "/look")
public class LookController {

    private static final String NO_RIGHTS_FOR_THIS_ACTION = "No rights for this action:";
    private static final String CODE_NOT_FOUND = "Code not found:";
    private static final String USER_NOT_FOUND = "User not found for login:";
    public static final String LOOK_CONTAINS_CLOTH = "Look already contains this cloth";
    public static final String LOOK_NOT_CONTAINS_CLOTH = "Look doesn't contains this cloth";

    @Autowired
    private LookFacade lookFacade;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private UserClothAttributeFacade userClothAttributeFacade;
    @Autowired
    private CommentService commentService;
    @Autowired
    @Qualifier("lookDataToFormConverter")
    private AbstractConverter lookDataToFormConverter;
    @Autowired
    @Qualifier("lookFormToDataConverter")
    private AbstractConverter lookFormToDataConverter;

    @Value("${spring.look.limit}")
    private int lookLimit;

    @RequestMapping(value = "/get/{code}", method = GET)
    public ResponseEntity<?> getLookByCode(@PathVariable("code") long code, Principal principal) {
        if (!lookFacade.isLookNumberExists(code)) {
            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }

        if (isUserHasRights(code, principal)) {
            LookData lookData = lookFacade.findByCode(code);
            return new ResponseEntity<Object>(lookData, OK);
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
    }

    @GetMapping(value = "/get/all/{login}")
    public ResponseEntity<?> getLooksByLogin(@PathVariable("login") String login, Principal principal) {
        if (!userFacade.isUserExists(login)) {
            return processingErrors(USER_NOT_FOUND + login, VALIDATION_TYPE_ERROR);
        }
        if (principal.getName().equalsIgnoreCase(login) || userFacade.isUserHasAdminRights(principal.getName())) {
            List<LookData> looks = lookFacade.findAllUserLooks(login);
            return new ResponseEntity<Object>(looks, OK);
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }

    }

    @GetMapping("/top")
    public ResponseEntity getLooksForUser(@RequestParam int minTemp, @RequestParam int maxTemp, Principal principal) {
        UserData sessionUser = userFacade.getUserByLogin(principal.getName());
        List<LookData> looks = lookFacade.findMostPopularUserLooks(sessionUser.getLogin(), minTemp, maxTemp, lookLimit, sessionUser.getSex());
        if (looks.isEmpty()) {
            return processingErrors("No looks found", NO_LOOKS_ERROR);
        }
        return ResponseEntity.ok(looks);
    }

    @PostMapping
    public ResponseEntity saveLook(@RequestBody LookForm lookForm, Principal principal) {
        lookForm.setLikes(0);
        lookForm.setComments(emptyList());
        lookForm.setUserLogin(principal.getName());
        LookData lookData = (LookData) lookFormToDataConverter.convert(lookForm);
        return ok().body(lookFacade.saveLook(lookData));
    }

    @PostMapping("/comment/{code}")
    public ResponseEntity addComment(@PathVariable long code, @RequestParam String comment, Principal principal) {

        if (!lookFacade.isLookNumberExists(code)) {
            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }
        return ok().body(commentService.addComment(principal.getName(), comment, code));
    }

    @PutMapping("/comment/{commentCode}")
    public ResponseEntity updateComment(@PathVariable("commentCode") long code, @RequestParam String message, Principal principal) {

        Comment comment = commentService.findCommentById(code);
        if (isNull(comment) || !comment.getLogin().equals(principal.getName())) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
        comment.setMessage(message);
        comment.setLastUpdated(new Date());
        commentService.save(comment);
        return ok().build();
    }

    @DeleteMapping("/comment/{commentCode}")
    public ResponseEntity removeComment(@PathVariable("commentCode") long code, Principal principal) {

        Comment comment = commentService.findCommentById(code);
        if (isNull(comment) || !comment.getLogin().equals(principal.getName())) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
        commentService.removeComment(code);
        return ok().build();
    }

    @PutMapping
    public ResponseEntity updateLook(@RequestBody LookForm lookForm, Principal principal) {

        if (!lookFacade.isLookNumberExists(lookForm.getCode())) {
            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }
        if (!isUserHasRights(lookForm.getCode(), principal)) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }

        LookData lookData = (LookData) lookFormToDataConverter.convert(lookForm);
        lookData.setCode(lookForm.getCode());
        lookFacade.updateLook(lookData);
        return ok().build();
    }

    @PostMapping("/like/{code}")
    public ResponseEntity addLike(@PathVariable long code, Principal principal) {
        if (!lookFacade.isLookNumberExists(code)) {
            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }
        return ok().body(lookFacade.addLike(principal.getName(), code));
    }

    @DeleteMapping("/like/{code}")
    public ResponseEntity deleteLike(@PathVariable long code, Principal principal) {
        if (!lookFacade.isLookNumberExists(code)) {
            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }
        return ok().body(lookFacade.removeLike(principal.getName(), code));
    }

    @DeleteMapping(value = "/delete/{code}")
    public ResponseEntity<?> deleteLookByCode(@PathVariable("code") long code, Principal principal) {
        if (!lookFacade.isLookNumberExists(code)) {

            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }

        if (isUserHasRights(code, principal)) {
            Look look = lookFacade.findModelByCode(code);
            lookFacade.deleteLookByCode(look.getCode());
            return new ResponseEntity(OK);
        } else {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION + principal.getName(), PERMISSION_TYPE_ERROR);
        }
    }

    @PostMapping("/picture/{code}")
    public ResponseEntity savePicture(@RequestParam("file") MultipartFile file, @PathVariable Long code, Principal principal) {

        if (!lookFacade.isLookNumberExists(code)) {
            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }

        if (!isUserHasRights(code, principal)) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION, PERMISSION_TYPE_ERROR);
        }
        lookFacade.savePicture(file, code);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{lookCode}/cloth/{clothCode}")
    public ResponseEntity addClothToLook(@PathVariable long lookCode, @PathVariable long clothCode, Principal principal) {
        LookData lookData = lookFacade.findByCode(lookCode);

        if (isNull(lookData)) {
            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }
        if (!isUserHasRights(lookCode, principal)) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION, PERMISSION_TYPE_ERROR);
        }
        UserClothAttributeData attribute = userClothAttributeFacade.get(clothCode);

        if (!hasPermissions(principal, attribute)) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION, PERMISSION_TYPE_ERROR);
        }

        if (isExists(lookData, attribute)) {
            return processingErrors(LOOK_CONTAINS_CLOTH, VALIDATION_TYPE_ERROR);
        }
        lookFacade.addClothToLook(lookCode, clothCode);
        return ResponseEntity.ok().build();
    }

    private boolean hasPermissions(Principal principal, UserClothAttributeData attribute) {
        return attribute.getUserData().getLogin().equalsIgnoreCase(principal.getName()) || attribute.isPublic() || userFacade.isUserHasAdminRights(principal.getName());
    }

    @DeleteMapping("/{lookCode}/cloth/{clothCode}")
    public ResponseEntity removeClothFromLook(@PathVariable long lookCode, @PathVariable long clothCode, Principal principal) {
        LookData lookData = lookFacade.findByCode(lookCode);

        if (isNull(lookData)) {
            return processingErrors(CODE_NOT_FOUND + principal.getName(), VALIDATION_TYPE_ERROR);
        }
        if (!isUserHasRights(lookCode, principal)) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION, PERMISSION_TYPE_ERROR);
        }
        UserClothAttributeData attribute = userClothAttributeFacade.get(clothCode);

        if (!hasPermissions(principal, attribute)) {
            return processingErrors(NO_RIGHTS_FOR_THIS_ACTION, PERMISSION_TYPE_ERROR);
        }

        if (!isExists(lookData, attribute)) {
            return processingErrors(LOOK_NOT_CONTAINS_CLOTH, VALIDATION_TYPE_ERROR);
        }
        lookFacade.removeClothFromLook(lookCode, clothCode);
        return ResponseEntity.ok().build();
    }

    private boolean isExists(LookData lookData, UserClothAttributeData attribute) {
        return lookData.getUserClothAttributes().stream().anyMatch(userClothAttributeData -> userClothAttributeData.getCode() == attribute.getCode());
    }

    private boolean isUserHasRights(long code, Principal principal) {
        return userFacade.isUserHasAdminRights(principal.getName()) || lookFacade.isPrincipalLook(code, principal.getName()) || lookFacade.isLookPublic(code);
    }
}
