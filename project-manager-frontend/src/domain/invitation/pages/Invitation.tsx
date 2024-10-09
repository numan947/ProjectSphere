import { Card, CardBody, CardHeader, Divider, Text } from "@chakra-ui/react";
import { InvitationResponse } from "../Entities";
import { useNavigate } from "react-router-dom";

interface InvitationProps {
  invitation: InvitationResponse;
}

const Invitation = ({ invitation }: InvitationProps) => {
  const dt = new Date(invitation.expiryDate);
  const navigate = useNavigate();

  return (
    <Card
      padding={1}
      rounded={15}
      border={"1px solid green"}
      onClick={() => {
        navigate("/accept-invitation", { state: invitation });
      }}
      _hover={{
        cursor: "pointer",
        bg: "gray.100",
      }}
      backgroundColor={"gray.50"}
      _active={{
        bg: "gray.200",
      }}
    >
      <CardHeader>
        <Text fontWeight="bold">{invitation.projectName}</Text>
        <Divider />
      </CardHeader>
      <CardBody>
        <Text>Expires At: {dt.toDateString()}</Text>
      </CardBody>
    </Card>
  );
};

export default Invitation;
