import {
  Badge,
  Button,
  Card,
  CardBody,
  CardHeader,
  Divider,
  IconButton,
  Text,
  useDisclosure,
  VStack,
} from "@chakra-ui/react";
import { ProjectShortResponse } from "../Entities";
import { AddIcon } from "@chakra-ui/icons";
import EmailAddressModal from "../../../common/EmailAddressModal";
import useCreateInvitations from "../../invitation/hooks/useCreateInvitations";

interface ProjectCardProps {
  project: ProjectShortResponse;
}
const ProjectCard = ({ project }: ProjectCardProps) => {
  const {
    isOpen: invitationModalIsOpen,
    onClose: invitationModalOnClose,
    onOpen: invidationModalOnOpen,
  } = useDisclosure();

  const { mutate: createInvitations, isPending: sendingInvitation } =
    useCreateInvitations();

  return (
    <Card padding={1} margin={1}>
      <CardHeader>
        <Text
          fontWeight="bold"
          as="h1"
          fontSize={{
            base: "lg",
            sm: "lg",
            md: "2xl",
          }}
          mb="10px"
        >
          {project.name}
        </Text>
        <Divider mt={2} mb={2} />
      </CardHeader>
      <CardBody mt={-34}>
        <VStack spacing={1} align={"start"}>
          <Text fontWeight="bold">
            Categories:{" "}
            {project.categories.map((category, index) => (
              <Badge key={index} colorScheme="purple" mr={1}>
                {category}
              </Badge>
            ))}
          </Text>
          <Text fontWeight="bold">
            Tags:{" "}
            {project.tags.map((tag, index) => (
              <Badge key={index} colorScheme="blue" mr={1}>
                {tag}
              </Badge>
            ))}
          </Text>
          <Divider mt={2} mb={2} />
          <Text fontWeight="bold">
            Members:{" "}
            <Badge colorScheme="orange" variant="outline">
              {project.memberCount}
            </Badge>
            <>
              <IconButton
                size="xs"
                icon={<AddIcon />}
                aria-label="Invite member"
                variant="ghost"
                colorScheme="orange"
                isRound
                onClick={invidationModalOnOpen}
              />
              <EmailAddressModal
                multi={true}
                isOpen={invitationModalIsOpen}
                onClose={invitationModalOnClose}
                headerText="Invite Member"
                placeholder="Enter email addresses"
                bodyText="Enter the email addresses of the member you want to invite, separated by commas."
                submitButtonText="Invite"
                onSubmit={(emails: string) => {
                  createInvitations({
                    projectId: project.id,
                    emails: emails.split(",").map((email) => email.trim()),
                  });
                }}
              />
            </>
          </Text>
          <Text fontWeight="bold">
            Issues:{" "}
            <Badge colorScheme="green" variant="outline">
              {project.issueCount}
            </Badge>
            <IconButton
              size="xs"
              icon={<AddIcon />}
              aria-label="Create new issue"
              variant="ghost"
              colorScheme="green"
              isRound
            />
          </Text>
        </VStack>
      </CardBody>
    </Card>
  );
};

export default ProjectCard;
