import {
  Card,
  CardBody,
  CardHeader,
  Divider,
  Flex,
  Skeleton,
  Text,
} from "@chakra-ui/react";
import { FaHandsHelping } from "react-icons/fa";
import { useGetPendingInvitations } from "../hooks/useGetInvitations";
import Invitation from "./Invitation";

const InvitationList = () => {
  const { data, isLoading, error } = useGetPendingInvitations();
  return (
    <>
      <Card padding={2} margin={3}>
        <CardHeader>
          <Flex justify="space-between">
            <Text fontWeight="bold" mb={2}>
              Invitations
            </Text>
            <FaHandsHelping />
          </Flex>
          <Divider />
        </CardHeader>
        <CardBody>
          {data && data.length > 0 ? (
            data.map((invitation, index) => (
              <Skeleton isLoaded={!isLoading} key={index}>
                <Invitation invitation={invitation} />
              </Skeleton>
            ))
          ) : (
            <Text>No pending invitations</Text>
          )}
        </CardBody>
      </Card>
    </>
  );
};

export default InvitationList;
