import {
  Modal,
  ModalBody,
  ModalContent,
  ModalHeader,
  ModalOverlay,
} from "@chakra-ui/modal";
import CreateProjectForm from "./CreateProjectForm";

interface CreateProjectModalProps {
  isOpen: boolean;
  onClose: () => void;
  onCreatedSuccess: () => void;
  onCreatedError: (msg: string) => void;
  onCancelled: () => void;
}

const CreateProjectModal = ({
  isOpen,
  onClose,
  onCancelled,
  onCreatedError,
  onCreatedSuccess,
}: CreateProjectModalProps) => {
  return (
    <Modal
      isOpen={isOpen}
      onClose={onClose}
      closeOnEsc={false}
      closeOnOverlayClick={false}
    >
      {/* Content */}
      <ModalOverlay />
      <ModalContent
        maxWidth={{ base: "90%", md: "600px", lg: "800px" }} // Adjust maxWidth based on screen size
        minWidth={{ base: "80%", md: "400px" }} // Adjust minWidth based on screen size
        minHeight={{ base: "200px", md: "300px" }} // Adjust minHeight based on screen size
      >
        <ModalHeader>Create Project</ModalHeader>
        <ModalBody>
          <CreateProjectForm
            onCancelled={onCancelled}
            onCreatedError={onCreatedError}
            onCreatedSuccess={onCreatedSuccess}
          />
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default CreateProjectModal;
