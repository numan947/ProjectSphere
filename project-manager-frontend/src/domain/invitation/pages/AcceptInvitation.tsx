import { useLocation, useNavigate } from "react-router-dom";
import { InvitationResponse } from "../Entities";
import { VStack, Text, Button, Alert, AlertIcon, Flex } from "@chakra-ui/react";
import useDeleteOrRejectInvitation from "../hooks/useDeleteOrRejectInvitation";
import useAcceptInvitation from "../hooks/useAcceptInvitation";

const AcceptInvitation = () => {
  const loc = useLocation();
  const navigate = useNavigate();

  if (!loc.state || (loc.state as InvitationResponse).invitationCode === "") {
    navigate("/home");
  }

  const invitation = loc.state as InvitationResponse;
  const dt = new Date(invitation.expiryDate);

  const {
    mutate: deleteOrRejectInvitation,
    isPending: waitingForDeleteOrReject,
    isSuccess: deleteOrRejectSuccess,
  } = useDeleteOrRejectInvitation();

  const {
    mutate: acceptInvitation,
    isPending: waitingForAccept,
    isSuccess: acceptSuccess,
  } = useAcceptInvitation();

  return (
    <VStack>
      <Text
        fontSize={{
          base: "xl",
          md: "2xl",
          lg: "3xl",
        }}
      >
        You have been invited to the following project:
      </Text>
      <Text
        fontWeight={"bold"}
        fontSize={{
          base: "lg",
          md: "xl",
          lg: "2xl",
        }}
      >
        Project Name: {invitation.projectName}
      </Text>
      <Text
        fontWeight={"bold"}
        fontSize={{
          base: "lg",
          md: "xl",
          lg: "2xl",
        }}
      >
        Invitation Code: {invitation.invitationCode}
      </Text>
      <Text
        fontWeight={"bold"}
        fontSize={{
          base: "lg",
          md: "xl",
          lg: "2xl",
        }}
        mb={5}
      >
        Expires At: {dt.toDateString()}
        {", "}
        {dt.toLocaleTimeString()}
      </Text>

      {dt.getTime() < Date.now() ? (
        <Text
          color={"red.500"}
          fontSize={{
            base: "lg",
            md: "xl",
            lg: "2xl",
          }}
        >
          This invitation has expired.
        </Text>
      ) : (
        !acceptSuccess &&
        !deleteOrRejectSuccess && (
          <Button
            variant={"outline"}
            colorScheme="green"
            _hover={{
              bg: "green.100",
            }}
            _active={{
              bg: "green.200",
            }}
            onClick={() => {
              acceptInvitation(invitation.invitationId);
            }}
            isLoading={waitingForAccept}
            isDisabled={acceptSuccess}
          >
            Accept
          </Button>
        )
      )}

      {!deleteOrRejectSuccess && !acceptSuccess && (
        <Button
          variant={"outline"}
          colorScheme="red"
          _hover={{
            bg: "red.100",
          }}
          _active={{
            bg: "red.200",
          }}
          onClick={() => {
            deleteOrRejectInvitation(invitation.invitationId);
          }}
          isLoading={waitingForDeleteOrReject}
          isDisabled={deleteOrRejectSuccess}
        >
          Reject
        </Button>
      )}

      {acceptSuccess && (
        <Flex justifyContent={"center"}>
          <Alert status="success">
            <AlertIcon />
            Invitation Accepted Successfully!
          </Alert>
        </Flex>
      )}
      {deleteOrRejectSuccess && (
        <Flex justifyContent={"center"}>
          <Alert status="error">
            <AlertIcon />
            Invitation Rejected Successfully!
          </Alert>
        </Flex>
      )}
    </VStack>
  );
};

export default AcceptInvitation;
