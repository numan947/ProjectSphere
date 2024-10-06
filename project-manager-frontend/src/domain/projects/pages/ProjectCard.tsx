import {
  Badge,
  Card,
  CardBody,
  CardHeader,
  Divider,
  Text,
  VStack,
} from "@chakra-ui/react";
import { ProjectShortResponse } from "../Entities";

interface ProjectCardProps {
  project: ProjectShortResponse;
}
const ProjectCard = ({ project }: ProjectCardProps) => {
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
          </Text>
          <Text fontWeight="bold">
            Issues:{" "}
            <Badge colorScheme="green" variant="outline">
              {project.issueCount}
            </Badge>
          </Text>
        </VStack>
      </CardBody>
    </Card>
  );
};

export default ProjectCard;
