package org.dfm.piggyurl.rest;

import java.util.List;
import org.dfm.piggyurl.domain.common.UserGroupType;
import org.dfm.piggyurl.domain.model.Card;
import org.dfm.piggyurl.domain.model.CardUpdate;
import org.dfm.piggyurl.domain.port.RequestCard;
import org.dfm.piggyurl.rest.request.CardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/piggyurls")
public class CardResource {

  @Value("${card.expirationDuration}")
  private String cardExpirationDurationInDays;

  @Autowired
  @Qualifier("requestCard")
  private RequestCard requestCard;

  @PostMapping("/cards")
  public ResponseEntity<Card> createCard(@RequestParam final String userNameOfCreator,
      @RequestBody final CardRequest cardRequest) {
    return ResponseEntity.ok(requestCard.createCard(userNameOfCreator, cardRequest.getUrlOriginal(),
        cardRequest.getShortUrlLevel(), cardRequest.getDescription(),
        cardRequest.getExpirationDate(),
        Integer.valueOf(cardExpirationDurationInDays).intValue()));
  }

  @PutMapping("/cards")
  public ResponseEntity<Card> updateCard(@RequestParam final String userNameOfCreator,
      @RequestBody final Card cardToBeUpdated) {
    return ResponseEntity.ok(requestCard.updateCard(userNameOfCreator, cardToBeUpdated));
  }

  @PostMapping("/cards/approves")
  public ResponseEntity<List<Card>> approveCard(@RequestParam final String approverUserName) {
    return ResponseEntity.ok(requestCard.approveCard(approverUserName));
  }

  @GetMapping("/cards/approvals")
  public ResponseEntity<List<CardUpdate>> getCardUpdatesForApproval(
      @RequestParam final String userName) {
    return ResponseEntity.ok(requestCard.getCardUpdatesForApproval(userName));
  }

  @GetMapping("/Groups/{groupName}/approvedCards")
  public ResponseEntity<List<Card>> getApprovedCardsForGroup(@PathVariable final String groupName,
      @RequestParam final UserGroupType groupType) {
    return ResponseEntity.ok(requestCard.getApprovedCardsForGroup(groupName, groupType));
  }
}